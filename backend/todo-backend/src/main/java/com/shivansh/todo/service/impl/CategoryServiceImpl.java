package com.shivansh.todo.service.impl;

import com.shivansh.todo.dataaccess.dto.CategoryResponse;
import com.shivansh.todo.dataaccess.dto.CreateCategoryRequest;
import com.shivansh.todo.dataaccess.entity.Category;
import com.shivansh.todo.dataaccess.mapper.CategoryMapper;
import com.shivansh.todo.dataaccess.repository.CategoryRepository;
import com.shivansh.todo.dataaccess.repository.UserRepository;
import com.shivansh.todo.exception.CategoryException;
import com.shivansh.todo.exception.Error;
import com.shivansh.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private Mono<Long> getCurrentUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new RuntimeException("Security context not found")))
                .map(context -> context.getAuthentication().getName())
                .map(Long::parseLong)
                .doOnNext(userId -> log.debug("Authenticated userId extracted: {}", userId));
    }

    @Override
    public Mono<CategoryResponse> createCategory(CreateCategoryRequest request) {
        request.setCategoryName(request.getCategoryName().trim().toLowerCase());
        if ("general".equalsIgnoreCase(request.getCategoryName())) {
            return Mono.error(new CategoryException(Error.CATEGORY_RESERVED));
        }
        return getCurrentUserId()
                .flatMap(userId -> {
                    log.info("User {} attempting to create category '{}'", userId, request.getCategoryName());
                    return categoryRepository
                            .findByUserIdAndCategoryNameAndIsActiveTrue(userId, request.getCategoryName())
                            .flatMap(existingCategory -> {
                                log.warn("Category '{}' already exists for user {}", request.getCategoryName(), userId);
                                return Mono.<CategoryResponse>error(new CategoryException(Error.CATEGORY_ALREADY_EXIST));
                            })
                            .switchIfEmpty(
                                    Mono.defer(() -> {
                                        Category category = CategoryMapper.toCategory(request, userId);
                                        log.info("Creating category '{}' for user {}", request.getCategoryName(), userId);
                                        return categoryRepository
                                                .save(category)
                                                .map(CategoryMapper::toResponse);
                                    })
                            );
                });
    }

    @Override
    public Flux<CategoryResponse> getCategories() {
        return getCurrentUserId()
                .doOnNext(userId -> log.info("Fetching categories for user {}", userId))
                .flatMapMany(categoryRepository::findByUserIdAndIsActiveTrue)
                .map(CategoryMapper::toResponse);
    }

    @Override
    public Mono<Void> deleteCategory(Long categoryId) {
        return getCurrentUserId()
                .flatMap(userId -> {
                    log.info("User {} attempting to delete category {}", userId, categoryId);
                    return categoryRepository
                            .findByUserIdAndCategoryIdAndIsActiveTrue(userId, categoryId);
                })
                .flatMap(existingCategory -> {
                    if ("general".equalsIgnoreCase(existingCategory.getCategoryName())) {
                        log.warn("Attempt to delete reserved category 'general'");
                        return Mono.error(new CategoryException(Error.CATEGORY_RESERVED));
                    }
                    existingCategory.setIsActive(false);
                    log.info("Soft deleting category {}", existingCategory.getCategoryId());
                    return categoryRepository.save(existingCategory);
                })
                .switchIfEmpty(
                        Mono.error(new CategoryException(Error.CATEGORY_DOES_NOT_EXIST))
                )
                .then();
    }

    @Override
    public Mono<CategoryResponse> getCategory(Long categoryId) {
        return getCurrentUserId()
                .flatMap(userId -> {
                    log.info("Fetching category {} for user {}", categoryId, userId);
                    return categoryRepository
                            .findByUserIdAndCategoryIdAndIsActiveTrue(userId, categoryId)
                            .switchIfEmpty(
                                    Mono.error(new CategoryException(Error.CATEGORY_DOES_NOT_EXIST))
                            );
                })
                .map(CategoryMapper::toResponse);
    }
}

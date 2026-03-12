package com.shivansh.todo.dataaccess.repository;

import com.shivansh.todo.dataaccess.entity.Category;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveCrudRepository<Category, Long> {

    Flux<Category> findByUserIdAndIsActiveTrue(Long userId);

    Mono<Category> findByUserIdAndCategoryIdAndIsActiveTrue(Long userId, Long categoryId);

    Mono<Category> findByUserIdAndCategoryNameAndIsActiveTrue(Long userId, String categoryName);

}

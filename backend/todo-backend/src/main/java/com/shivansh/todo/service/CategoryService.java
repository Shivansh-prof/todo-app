package com.shivansh.todo.service;

import com.shivansh.todo.dataaccess.dto.CategoryResponse;
import com.shivansh.todo.dataaccess.dto.CreateCategoryRequest;
import com.shivansh.todo.dataaccess.dto.CategoryRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    Mono<CategoryResponse> createCategory(CreateCategoryRequest request);

    Flux<CategoryResponse> getCategories();

    Mono<Void> deleteCategory(Long categoryId);

    Mono<CategoryResponse> getCategory(Long categoryId);
}

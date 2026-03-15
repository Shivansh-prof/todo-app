package com.shivansh.todo.controller;

import com.shivansh.todo.dataaccess.dto.*;
import com.shivansh.todo.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public Mono<CategoryResponse> createCategory(@RequestBody CreateCategoryRequest request) {
        return categoryService.createCategory(request);
    }

    @GetMapping
    public Flux<CategoryResponse> getCategories() {
        return categoryService.getCategories();
    }

    @GetMapping("/{categoryId}")
    public Mono<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @DeleteMapping("/{categoryId}")
    public Mono<Void> deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
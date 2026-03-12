package com.shivansh.todo.dataaccess.mapper;

import com.shivansh.todo.dataaccess.dto.CategoryResponse;
import com.shivansh.todo.dataaccess.dto.CreateCategoryRequest;
import com.shivansh.todo.dataaccess.entity.Category;

import java.time.LocalDateTime;

public class CategoryMapper {

    public static Category toCategory(CreateCategoryRequest request, Long userId) {

        Category category = new Category();
        category.setCategoryName(request.getCategoryName());
        category.setUserId(userId);
        category.setIsActive(Boolean.TRUE);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return category;
    }

    public static CategoryResponse toResponse(Category category) {

        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .categoryName(category.getCategoryName())
                .build();
    }

}

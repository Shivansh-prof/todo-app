package com.shivansh.todo.dataaccess.dto;

import lombok.Data;

@Data
public class CategoryRequest {
    private Long userId;
    private Long categoryId;
}
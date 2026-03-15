package com.shivansh.todo.dataaccess.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTodoRequest {
    private String title;
    private String description;
    private LocalDate taskDate;
    private Long categoryId;
}
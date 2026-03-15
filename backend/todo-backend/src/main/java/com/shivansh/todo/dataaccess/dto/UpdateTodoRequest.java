package com.shivansh.todo.dataaccess.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTodoRequest {
    private String title;
    private String description;
    private Long categoryId;
    private LocalDate taskDate;
    private Boolean isCompleted;
}
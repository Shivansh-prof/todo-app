package com.shivansh.todo.dataaccess.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateTodoRequest {
    @NotBlank
    private String title;
    private String description;
    private Long categoryId;
    private LocalDate taskDate;
    private Boolean isCompleted;
}
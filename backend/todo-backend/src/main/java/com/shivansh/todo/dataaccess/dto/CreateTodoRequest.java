package com.shivansh.todo.dataaccess.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateTodoRequest {
    @NotBlank(message = "Title is Required")
    private String title;
    private String description;
    @NotNull(message = "Task Date is Required")
    private LocalDate taskDate;
    private Long categoryId;
}
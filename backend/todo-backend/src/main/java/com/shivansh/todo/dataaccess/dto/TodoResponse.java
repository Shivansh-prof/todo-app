package com.shivansh.todo.dataaccess.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoResponse {
    private Long taskId;
    private String title;
    private String description;
    private LocalDate taskDate;
    private Boolean isCompleted;
    private Long categoryId;
}
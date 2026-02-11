package com.shivansh.todo.dataaccess.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("todos")
@Data
@Builder
public class Todo {
    @Id
    private Long taskId ;
    private Long UserId;
    private String title;
    private String description;
    private LocalDate taskDate;
    private Boolean isCompleted;
    private Long categoryId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

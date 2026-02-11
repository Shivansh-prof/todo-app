package com.shivansh.todo.dataaccess.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table("repeat_tasks")
@Data
@Builder
public class RepeatedTodo {
    @Id
    private Long taskId;
    private Long UserId;
    private String title;
    private String description;
    private String repeatDaysCount;
    private LocalDate startDate;
    private Long categoryId;
    private LocalDateTime createdAt;
}

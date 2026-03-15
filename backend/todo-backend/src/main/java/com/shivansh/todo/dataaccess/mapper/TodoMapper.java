package com.shivansh.todo.dataaccess.mapper;

import com.shivansh.todo.dataaccess.dto.*;
import com.shivansh.todo.dataaccess.entity.Todo;

import java.time.LocalDateTime;

public class TodoMapper {

    public static Todo toEntity(CreateTodoRequest createTodoRequest, Long userId){
        return Todo.builder()
                .userId(userId)
                .title(createTodoRequest.getTitle())
                .description(createTodoRequest.getDescription())
                .taskDate(createTodoRequest.getTaskDate())
                .categoryId(createTodoRequest.getCategoryId())
                .isCompleted(Boolean.FALSE)
                .isActive(Boolean.TRUE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static void updateEntity(Todo todo, UpdateTodoRequest request){
        todo.setTitle(request.getTitle());
        todo.setDescription(request.getDescription());
        todo.setTaskDate(request.getTaskDate());
        todo.setCategoryId(request.getCategoryId());
        todo.setIsCompleted(request.getIsCompleted());
        todo.setUpdatedAt(LocalDateTime.now());
    }

    public static TodoResponse toResponse(Todo todo){
        return TodoResponse
                .builder()
                .taskId(todo.getTaskId())
                .title(todo.getTitle())
                .description(todo.getDescription())
                .taskDate(todo.getTaskDate())
                .isCompleted(todo.getIsCompleted())
                .categoryId(todo.getCategoryId())
                .build();
    }

}

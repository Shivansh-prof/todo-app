package com.shivansh.todo.service;

import com.shivansh.todo.dataaccess.dto.CreateTodoRequest;
import com.shivansh.todo.dataaccess.dto.TodoResponse;
import com.shivansh.todo.dataaccess.dto.UpdateTodoRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface TodoService {
    Mono<TodoResponse> createTodo(CreateTodoRequest request);

    Flux<TodoResponse> getTodos();

    Flux<TodoResponse> getCompletedTodos();

    Flux<TodoResponse> getPendingTodos();

    Flux<TodoResponse> getTodosByDate(LocalDate date);

    Flux<TodoResponse> getTodosByCategory(Long categoryId);

    Mono<Void> updateTodo(Long taskId, UpdateTodoRequest updateTodoRequest);

    Mono<Void> markTodoCompleted(Long taskId);

    Mono<Void> deleteTodo(Long taskId);

}

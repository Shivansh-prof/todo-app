package com.shivansh.todo.controller;

import com.shivansh.todo.dataaccess.dto.CreateTodoRequest;
import com.shivansh.todo.dataaccess.dto.TodoResponse;
import com.shivansh.todo.dataaccess.dto.UpdateTodoRequest;
import com.shivansh.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<TodoResponse> createTodo(@RequestBody CreateTodoRequest request) {
        return todoService.createTodo(request);
    }

    @GetMapping
    public Flux<TodoResponse> getTodos(){
        return todoService.getTodos();
    }

    @GetMapping("/completed")
    public Flux<TodoResponse> getCompletedTodos(){
        return todoService.getCompletedTodos();
    }

    @GetMapping("/pending")
    public Flux<TodoResponse> getPendingTodos(){
        return todoService.getPendingTodos();
    }

    @GetMapping("/date/{date}")
    public Flux<TodoResponse> getTodosByDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        return todoService.getTodosByDate(date);
    }

    @GetMapping("/category/{categoryId}")
    public Flux<TodoResponse> getTodosByCategory(@PathVariable Long categoryId){
        return todoService.getTodosByCategory(categoryId);
    }

    @PutMapping("/{taskId}")
    public Mono<Void> updateTodo(@PathVariable Long taskId, @RequestBody UpdateTodoRequest updateTodoRequest){
        return todoService.updateTodo(taskId,updateTodoRequest);
    }
    @PatchMapping("/{taskId}/complete")
    public Mono<Void> markTodoCompleted(@PathVariable Long taskId){
        return todoService.markTodoCompleted(taskId);
    }

    @DeleteMapping("/{taskId}")
    public Mono<Void> deleteTodo(@PathVariable Long taskId){
        return todoService.deleteTodo(taskId);
    }

}
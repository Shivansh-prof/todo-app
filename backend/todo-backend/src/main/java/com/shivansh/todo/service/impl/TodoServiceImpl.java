package com.shivansh.todo.service.impl;

import com.shivansh.todo.dataaccess.dto.CreateTodoRequest;
import com.shivansh.todo.dataaccess.dto.TodoResponse;
import com.shivansh.todo.dataaccess.dto.UpdateTodoRequest;
import com.shivansh.todo.dataaccess.entity.Category;
import com.shivansh.todo.dataaccess.entity.Todo;
import com.shivansh.todo.dataaccess.mapper.TodoMapper;
import com.shivansh.todo.dataaccess.repository.CategoryRepository;
import com.shivansh.todo.dataaccess.repository.TodoRepository;
import com.shivansh.todo.exception.Error;
import com.shivansh.todo.exception.TodoException;
import com.shivansh.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final CategoryRepository categoryRepository;

    private Mono<Long> getCurrentUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .switchIfEmpty(Mono.error(new RuntimeException("Security context not found")))
                .map(context -> context.getAuthentication().getName())
                .map(Long::parseLong)
                .doOnNext(userId -> log.debug("Authenticated userId extracted: {}", userId));
    }

    @Override
    public Mono<TodoResponse> createTodo(CreateTodoRequest request) {
        return getCurrentUserId()
                .flatMap(userId ->{
                    log.info("User {} attempting to create todo '{}'", userId, request.getTitle());
                    Mono<Long> categoryIdMono;
                    if (request.getCategoryId()==null){
                        log.debug("No category provided, resolving default 'general' category for user {}", userId);
                        categoryIdMono = categoryRepository.findByUserIdAndCategoryNameAndIsActiveTrue(userId,"general")
                                .switchIfEmpty(Mono.error(new TodoException(Error.CATEGORY_DOES_NOT_EXIST)))
                                .map(Category::getCategoryId);
                    }else {
                        log.debug("Validating category {} for user {}", request.getCategoryId(), userId);
                        categoryIdMono = categoryRepository.findByUserIdAndCategoryIdAndIsActiveTrue(userId, request.getCategoryId())
                                .switchIfEmpty(Mono.error(new TodoException(Error.CATEGORY_DOES_NOT_EXIST)))
                                .map(Category::getCategoryId);
                    }
                    return categoryIdMono.flatMap(categoryId ->{
                        request.setCategoryId(categoryId);
                        Todo todo = TodoMapper.toEntity(request,userId);
                        log.info("Creating todo {} for user {} under category {}",request.getTitle(),userId,categoryId);
                        return todoRepository.save(todo)
                                .doOnSuccess(saved -> log.info("Todo {} created successfully for user {}", saved.getTaskId(), userId))
                                .map(TodoMapper::toResponse);
                    });
                });

    }

    @Override
    public Flux<TodoResponse> getTodos() {
        return getCurrentUserId()
                .doOnNext(userId-> log.info("Fetching Active todos for user {}",userId))
                .flatMapMany(userId ->
                        todoRepository.findByUserIdAndIsActiveTrue(userId)
                        .map(TodoMapper::toResponse));
    }

    @Override
    public Flux<TodoResponse> getCompletedTodos() {
        return getCurrentUserId()
                .doOnNext(userId-> log.info("Fetching Completed todos for user {}",userId))
                .flatMapMany(userId ->
                        todoRepository.findByUserIdAndIsCompletedAndIsActiveTrue(userId,Boolean.TRUE)
                                .map(TodoMapper::toResponse));
    }

    @Override
    public Flux<TodoResponse> getPendingTodos() {
        return getCurrentUserId()
                .doOnNext(userId-> log.info("Fetching Pending todos for user {}",userId))
                .flatMapMany(userId ->
                        todoRepository.findByUserIdAndIsCompletedAndIsActiveTrue(userId,Boolean.FALSE)
                                .map(TodoMapper::toResponse));
    }

    @Override
    public Flux<TodoResponse> getTodosByDate(LocalDate date) {
        return getCurrentUserId()
                .doOnNext(userId-> log.debug("Fetching all Active todos for user {} for date {}",userId,date))
                .flatMapMany(userId ->
                        todoRepository.findByUserIdAndTaskDateAndIsActiveTrue(userId,date)
                                .map(TodoMapper::toResponse));
    }

    @Override
    public Flux<TodoResponse> getTodosByCategory(Long categoryId) {
        return getCurrentUserId()
                .doOnNext(userId-> log.info("Fetching Active todos for user {} in category {}",userId,categoryId))
                .flatMapMany(userId->
                        categoryRepository.findByUserIdAndCategoryIdAndIsActiveTrue(userId,categoryId)
                                .switchIfEmpty(Mono.error(new TodoException(Error.CATEGORY_DOES_NOT_EXIST)))
                                .flatMapMany(category ->todoRepository.findByUserIdAndCategoryIdAndIsActiveTrue(userId,categoryId))
                )
                .map(TodoMapper::toResponse);
    }

    @Override
    public Mono<Void> updateTodo(Long taskId, UpdateTodoRequest updateTodoRequest) {
        return getCurrentUserId()
                .doOnNext(userId-> log.info("User {} updating todo {}",userId,taskId))
                .flatMap(userId ->
                        getActiveTodos(userId,taskId)
                                .flatMap(todo -> {
                                    TodoMapper.updateEntity(todo,updateTodoRequest);
                                    log.debug("Updating todo {} for user {}",taskId,userId);
                                    return todoRepository.save(todo);
                                })
                ).then();
    }

    @Override
    public Mono<Void> markTodoCompleted(Long taskId) {
        return getCurrentUserId()
                .doOnNext(userId-> log.info("User {} marking Todo {} as Completed",userId,taskId))
                .flatMap(userId ->
                        getActiveTodos(userId,taskId)
                                .flatMap(todo -> {
                                    todo.setIsCompleted(Boolean.TRUE);
                                    todo.setUpdatedAt(LocalDateTime.now());
                                    return todoRepository.save(todo);
                                })
                ).then();
    }

    @Override
    public Mono<Void> deleteTodo(Long taskId) {
        return getCurrentUserId()
                .doOnNext(userId-> log.info("User {} deleting Todo {}",userId,taskId))
                .flatMap(userId ->
                        getActiveTodos(userId,taskId)
                                .flatMap(todo -> {
                                    todo.setIsActive(Boolean.FALSE);
                                    todo.setUpdatedAt(LocalDateTime.now());
                                    log.info("Soft Deleting Todo {}",taskId);
                                    return todoRepository.save(todo);
                                })
                ).then();
    }

    private Mono<Todo> getActiveTodos (Long userId,Long taskId){
        return todoRepository.findByUserIdAndTaskIdAndIsActiveTrue(userId,taskId)
                .switchIfEmpty(Mono.error(new TodoException(Error.TODO_DOES_NOT_EXIST)))
                .doOnNext(todo -> log.debug("Todo {} found for user {}", taskId, userId));
    }
}

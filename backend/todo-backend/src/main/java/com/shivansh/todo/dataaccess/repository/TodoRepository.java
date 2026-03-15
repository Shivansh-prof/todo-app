package com.shivansh.todo.dataaccess.repository;

import com.shivansh.todo.dataaccess.entity.Todo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

public interface TodoRepository extends ReactiveCrudRepository<Todo, Long> {

    Flux<Todo> findByUserIdAndIsActiveTrue(Long userId);
    Flux<Todo> findByUserIdAndTaskDateAndIsActiveTrue(Long userId, LocalDate taskDate);
    Flux<Todo> findByUserIdAndCategoryIdAndIsActiveTrue(Long userId, Long categoryId);
    Flux<Todo> findByUserIdAndIsCompletedAndIsActiveTrue(Long userId, Boolean isCompleted);
    Mono<Todo> findByUserIdAndTaskIdAndIsActiveTrue(Long userId, Long taskId);
}

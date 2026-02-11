package com.shivansh.todo.dataaccess.repository;

import com.shivansh.todo.dataaccess.entity.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User,Long> {
    Mono<User> findByEmail(String email);
}

package com.shivansh.todo.controller;

import com.shivansh.todo.dataaccess.entity.User;
import com.shivansh.todo.dataaccess.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserTestController {
    private final UserRepository userRepository;

    @GetMapping
    private Flux<User> getAll(){
        return userRepository.findAll();
    }

    @PostMapping("/add")
    public Mono<User> createUser(@RequestBody User user) {
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        user.setIsActive(true);
        return userRepository.save(user);
    }
}

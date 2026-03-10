package com.shivansh.todo.controller;

import com.shivansh.todo.dataaccess.dto.AddUserRequest;
import com.shivansh.todo.dataaccess.dto.AddUserResponse;
import com.shivansh.todo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService service;

    @PostMapping("/add")
    public Mono<AddUserResponse> createUser(@RequestBody AddUserRequest addUserRequest) {
        log.info("Request received for add User for email {}",addUserRequest.getEmail());
        return service.save(addUserRequest);
    }
}

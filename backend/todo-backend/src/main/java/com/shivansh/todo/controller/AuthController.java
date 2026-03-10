package com.shivansh.todo.controller;

import com.shivansh.todo.dataaccess.dto.LoginRequest;
import com.shivansh.todo.dataaccess.dto.LoginResponse;
import com.shivansh.todo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public Mono<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        log.info("Request received for login for email {}",loginRequest.getEmail());
        return service.login(loginRequest);
    }
}

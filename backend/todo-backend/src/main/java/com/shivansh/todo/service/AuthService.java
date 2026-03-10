package com.shivansh.todo.service;

import com.shivansh.todo.dataaccess.dto.LoginRequest;
import com.shivansh.todo.dataaccess.dto.LoginResponse;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<LoginResponse> login(LoginRequest loginRequest);
}

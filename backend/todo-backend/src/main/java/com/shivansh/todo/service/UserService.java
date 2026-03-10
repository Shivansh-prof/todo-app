package com.shivansh.todo.service;

import com.shivansh.todo.dataaccess.dto.AddUserRequest;
import com.shivansh.todo.dataaccess.dto.AddUserResponse;
import com.shivansh.todo.dataaccess.dto.LoginRequest;
import com.shivansh.todo.dataaccess.dto.LoginResponse;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<AddUserResponse> save(AddUserRequest addUserRequest);

}

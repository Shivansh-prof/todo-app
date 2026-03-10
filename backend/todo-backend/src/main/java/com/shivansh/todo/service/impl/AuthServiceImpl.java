package com.shivansh.todo.service.impl;

import com.shivansh.todo.dataaccess.dto.LoginRequest;
import com.shivansh.todo.dataaccess.dto.LoginResponse;
import com.shivansh.todo.dataaccess.mapper.TodoUserMapper;
import com.shivansh.todo.dataaccess.repository.UserRepository;
import com.shivansh.todo.exception.Error;
import com.shivansh.todo.exception.UserException;
import com.shivansh.todo.security.JwtUtil;
import com.shivansh.todo.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    @Override
    public Mono<LoginResponse> login(LoginRequest loginRequest) {
        return userRepository.findByEmail(loginRequest.getEmail())
                .switchIfEmpty(Mono.error(new UserException(com.shivansh.todo.exception.Error.USER_NOT_FOUND)))
                .flatMap(user -> {
                    boolean passwordMaches = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
                    if(!passwordMaches){
                        return Mono.error(new UserException(Error.INVALID_PASSWORD));
                    }
                    String token = jwtUtil.generateToken(user.getEmail());
                    return Mono.just(TodoUserMapper.toLoginResponse(user,token));
                });
    }
}

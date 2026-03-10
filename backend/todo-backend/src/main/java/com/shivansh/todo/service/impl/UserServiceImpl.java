package com.shivansh.todo.service.impl;

import com.shivansh.todo.dataaccess.dto.AddUserRequest;
import com.shivansh.todo.dataaccess.dto.AddUserResponse;
import com.shivansh.todo.dataaccess.dto.LoginRequest;
import com.shivansh.todo.dataaccess.dto.LoginResponse;
import com.shivansh.todo.dataaccess.mapper.TodoUserMapper;
import com.shivansh.todo.dataaccess.repository.UserRepository;
import com.shivansh.todo.exception.Error;
import com.shivansh.todo.exception.UserException;
import com.shivansh.todo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Mono<AddUserResponse> save(AddUserRequest addUserRequest) {
        return userRepository.findByEmail(addUserRequest.getEmail())
                .flatMap(user -> Mono.<AddUserResponse>error(new UserException(Error.USER_ALREADY_EXIST)))
                .switchIfEmpty(
                        Mono.fromSupplier(() -> {
                                    String encodedPassword =
                                            passwordEncoder.encode(addUserRequest.getPassword());
                                    return TodoUserMapper.addUserRequestToUserEntity(
                                            addUserRequest, encodedPassword
                                    );
                                })
                                .flatMap(userRepository::save)
                                .map(TodoUserMapper::toAddUserResponse)
                );
    }

}

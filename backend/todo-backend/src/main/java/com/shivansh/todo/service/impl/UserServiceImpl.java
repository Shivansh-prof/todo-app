package com.shivansh.todo.service.impl;

import com.shivansh.todo.dataaccess.dto.AddUserRequest;
import com.shivansh.todo.dataaccess.dto.AddUserResponse;
import com.shivansh.todo.dataaccess.entity.Category;
import com.shivansh.todo.dataaccess.entity.User;
import com.shivansh.todo.dataaccess.mapper.UserMapper;
import com.shivansh.todo.dataaccess.repository.CategoryRepository;
import com.shivansh.todo.dataaccess.repository.UserRepository;
import com.shivansh.todo.exception.Error;
import com.shivansh.todo.exception.UserException;
import com.shivansh.todo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;


@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryRepository categoryRepository;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Mono<AddUserResponse> save(AddUserRequest addUserRequest) {
        return userRepository.findByEmail(addUserRequest.getEmail())
                .flatMap(user -> Mono.<AddUserResponse>error(new UserException(Error.USER_ALREADY_EXIST)))
                .switchIfEmpty(
                        Mono.defer(() -> {
                            String encodedPassword =
                                    passwordEncoder.encode(addUserRequest.getPassword());
                            User userEntity =
                                    UserMapper.addUserRequestToUserEntity(addUserRequest, encodedPassword);
                            return userRepository.save(userEntity)
                                    .flatMap(savedUser -> {
                                        Category defaultCategory = new Category();
                                        defaultCategory.setCategoryName("general");
                                        defaultCategory.setUserId(savedUser.getUserId());
                                        defaultCategory.setIsActive(true);
                                        defaultCategory.setCreatedAt(LocalDateTime.now());

                                        return categoryRepository.save(defaultCategory)
                                                .thenReturn(savedUser);
                                    })
                                    .map(UserMapper::toAddUserResponse);
                        })
                );
    }

}

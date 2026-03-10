package com.shivansh.todo.dataaccess.mapper;

import com.shivansh.todo.dataaccess.dto.AddUserRequest;
import com.shivansh.todo.dataaccess.dto.AddUserResponse;
import com.shivansh.todo.dataaccess.dto.LoginResponse;
import com.shivansh.todo.dataaccess.entity.User;

import java.time.LocalDateTime;

public class TodoUserMapper {

    public static User addUserRequestToUserEntity(AddUserRequest request, String encodedPassword) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setIsActive(Boolean.TRUE);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return user;
    }

    public static AddUserResponse toAddUserResponse(User user) {

        AddUserResponse response = new AddUserResponse();
        response.setRemark("User added successfully");
        response.setIsSuccess(Boolean.TRUE);
        return response;
    }

    public static LoginResponse toLoginResponse(User user, String token){
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setRemark("Login Successful");
        loginResponse.setIsSuccess(Boolean.TRUE);
        loginResponse.setToken(token);
        return loginResponse;
    }
}
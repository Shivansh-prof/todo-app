package com.shivansh.todo.dataaccess.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddUserRequest {
    @Email
    @NotBlank(message = "email cannot be null or blank")
    private String email;
    @NotBlank(message = "password cannot be null or blank")
    private String password;
}

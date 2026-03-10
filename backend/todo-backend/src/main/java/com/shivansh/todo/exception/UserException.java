package com.shivansh.todo.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final Error error;

    public UserException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}

package com.shivansh.todo.exception;

import lombok.Getter;

@Getter
public class TodoException extends RuntimeException {

    private final Error error;

    public TodoException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}

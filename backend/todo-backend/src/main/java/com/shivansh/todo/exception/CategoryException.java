package com.shivansh.todo.exception;

import lombok.Getter;

@Getter
public class CategoryException extends RuntimeException {

    private final Error error;

    public CategoryException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}

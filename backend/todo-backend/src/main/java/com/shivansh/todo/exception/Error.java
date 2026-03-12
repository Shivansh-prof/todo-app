package com.shivansh.todo.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@RequiredArgsConstructor
@Getter
@ToString
public enum Error {
    USER_ALREADY_EXIST("USER_ALREADY_EXIST","User with Email Already Exists. "),
    USER_NOT_FOUND("USER_NOT_FOUND","User Not Found with given Email, Kindly Register!!!"),
    INVALID_PASSWORD("INVALID_PASSWORD","Invalid Email or Password"),
    CATEGORY_ALREADY_EXIST("CATEGORY_ALREADY_EXIST","Category with this name Already Exists. "),
    CATEGORY_DOES_NOT_EXIST("CATEGORY_DOES_NOT_EXIST","Category with this name Does Not Exists. "),
    CATEGORY_RESERVED("CATEGORY_RESERVED","Sorry this category cannot be added or deleted");
    private final String code;
    private final String message;
}

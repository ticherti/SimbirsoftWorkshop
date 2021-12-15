package com.github.ticherti.simplechat.exception;

public class UserNotFoundException extends RuntimeException {
    private static final String MESSAGE = "User not found ";

    public UserNotFoundException(Long id) {
        super(MESSAGE + id);
    }
}

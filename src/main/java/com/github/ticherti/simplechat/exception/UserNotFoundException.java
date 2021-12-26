package com.github.ticherti.simplechat.exception;

public class UserNotFoundException extends AbstractNotFoundException {
    private static final String MESSAGE = "User";

    public UserNotFoundException(Long id) {
        super(MESSAGE, id);
    }
}

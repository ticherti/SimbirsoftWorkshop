package com.github.ticherti.simplechat.exception;

public class MessageNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Message not found ";

    public MessageNotFoundException(Long id) {
        super(MESSAGE + id);
    }
}

package com.github.ticherti.simplechat.exception;

public class MessageNotFoundException extends AbstractNotFoundException {
    private static final String MESSAGE = "Message";

    public MessageNotFoundException(Long id) {
        super(MESSAGE, id);
    }
}

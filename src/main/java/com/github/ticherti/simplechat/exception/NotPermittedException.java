package com.github.ticherti.simplechat.exception;

public class NotPermittedException extends RuntimeException{
    public NotPermittedException(String message) {
        super(message);
    }
}

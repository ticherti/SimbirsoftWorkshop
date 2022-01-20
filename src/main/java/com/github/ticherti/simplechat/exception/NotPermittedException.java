package com.github.ticherti.simplechat.exception;

import org.springframework.security.access.AccessDeniedException;

public class NotPermittedException extends AccessDeniedException {
    public NotPermittedException(String message) {
        super(message);
    }
}

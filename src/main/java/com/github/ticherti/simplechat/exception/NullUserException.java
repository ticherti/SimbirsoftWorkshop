package com.github.ticherti.simplechat.exception;

public class NullUserException extends NullPointerException{
    public NullUserException() {
        super("The user is null");
    }
}

package com.github.ticherti.simplechat.exception;

public class NullMessageException extends NullPointerException{
    public NullMessageException() {
        super("The message is null");
    }
}

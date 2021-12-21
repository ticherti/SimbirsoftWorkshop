package com.github.ticherti.simplechat.exception;

public class NullRoomException extends NullPointerException{
    public NullRoomException() {
        super("The room is null");
    }
}

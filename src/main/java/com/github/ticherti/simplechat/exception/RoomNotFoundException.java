package com.github.ticherti.simplechat.exception;

public class RoomNotFoundException extends RuntimeException {
    private static final String MESSAGE = "Room not found ";

    public RoomNotFoundException(Long id) {
        super(MESSAGE + id);
    }
}

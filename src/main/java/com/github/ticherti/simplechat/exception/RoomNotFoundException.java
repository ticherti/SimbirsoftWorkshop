package com.github.ticherti.simplechat.exception;

public class RoomNotFoundException extends AbstractNotFoundException {
    private static final String MESSAGE = "Room";

    public RoomNotFoundException(Long id) {
        super(MESSAGE, id);
    }
}

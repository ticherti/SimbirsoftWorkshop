package com.github.ticherti.simplechat.exception;

import javax.persistence.EntityNotFoundException;

public abstract class AbstractNotFoundException extends EntityNotFoundException {

    public AbstractNotFoundException(String entity, Long id) {
        super(String.format("%s not found %d", entity, id));
    }
}
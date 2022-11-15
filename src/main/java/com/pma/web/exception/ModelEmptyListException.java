package com.pma.web.exception;

import java.io.Serial;

public class ModelEmptyListException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public ModelEmptyListException(String message) {
        super(message);

    }
}

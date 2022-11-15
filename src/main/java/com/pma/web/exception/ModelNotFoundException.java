package com.pma.web.exception;

import java.io.Serial;

public class ModelNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ModelNotFoundException(String message) {
        super(message);

    }
}

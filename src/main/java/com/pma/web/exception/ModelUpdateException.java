package com.pma.web.exception;

import java.io.Serial;

public class ModelUpdateException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 1L;

    public ModelUpdateException(String message) {
        super(message);

    }
}

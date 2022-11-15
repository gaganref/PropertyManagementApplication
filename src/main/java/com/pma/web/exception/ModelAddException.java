package com.pma.web.exception;

import java.io.Serial;

public class ModelAddException extends RuntimeException {


    @Serial
    private static final long serialVersionUID = 1L;

    public ModelAddException(String message) {
        super(message);

    }
}

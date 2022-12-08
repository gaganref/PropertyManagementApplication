package com.pma.web.exception;


public class ModelNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ModelNotFoundException(String message) {
        super(message);

    }
}

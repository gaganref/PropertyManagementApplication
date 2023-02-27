package com.pma.web.exception;


public class ModelEmptyListException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ModelEmptyListException(String message) {
        super(message);

    }
}

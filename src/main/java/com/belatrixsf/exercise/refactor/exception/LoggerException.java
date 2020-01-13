package com.belatrixsf.exercise.refactor.exception;

public class LoggerException extends Exception {

    private static final long serialVersionUID = -8265717287249763767L;

    public LoggerException(final String message) {
        super(message);
    }

}

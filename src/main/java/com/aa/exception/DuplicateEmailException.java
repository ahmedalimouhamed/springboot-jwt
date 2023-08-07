package com.aa.exception;

public class DuplicateEmailException extends Exception {
    private static final long serialVersionId = 1L;

    public DuplicateEmailException(String message) {
        super(message);
    }
}

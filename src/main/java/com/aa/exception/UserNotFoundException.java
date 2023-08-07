package com.aa.exception;

public class UserNotFoundException extends Exception {
    private static final long id = 1l;

    public UserNotFoundException(String message) {
        super(message);
    }
}

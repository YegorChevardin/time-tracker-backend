package com.reactdevops.timetracker.backend.service.exceptions;

public class CustomWebException extends RuntimeException {
    public CustomWebException(String message) {
        super(message);
    }

    public CustomWebException(String message, Throwable cause) {
        super(message, cause);
    }
}

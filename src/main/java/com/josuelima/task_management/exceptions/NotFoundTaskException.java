package com.josuelima.task_management.exceptions;

public class NotFoundTaskException extends RuntimeException {

    public NotFoundTaskException(String message) {
        super(message);
    }
}

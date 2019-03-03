package com.witchok.bootnet.exceptions;

public abstract class UserProcessingException extends RuntimeException {

    public UserProcessingException() {
    }

    public UserProcessingException(String message) {
        super(message);
    }
}

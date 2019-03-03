package com.witchok.bootnet.exceptions;

public class UserWithUsernameAlreadyExistsException extends UserProcessingException{
    public UserWithUsernameAlreadyExistsException() { }

    public UserWithUsernameAlreadyExistsException(String message) {
        super(message);
    }

}


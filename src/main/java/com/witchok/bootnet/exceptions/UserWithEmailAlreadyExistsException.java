package com.witchok.bootnet.exceptions;

public class UserWithEmailAlreadyExistsException extends UserProcessingException{
    public UserWithEmailAlreadyExistsException() { }

    public UserWithEmailAlreadyExistsException(String message) {
        super(message);
    }


}

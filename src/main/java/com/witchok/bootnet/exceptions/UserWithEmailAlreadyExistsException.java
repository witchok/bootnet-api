package com.witchok.bootnet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserWithEmailAlreadyExistsException extends RuntimeException{
    public UserWithEmailAlreadyExistsException() {
    }

    public UserWithEmailAlreadyExistsException(String message) {
        super(message);
    }
}

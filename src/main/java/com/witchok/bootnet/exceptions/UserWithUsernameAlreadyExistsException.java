package com.witchok.bootnet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserWithUsernameAlreadyExistsException extends RuntimeException{
    public UserWithUsernameAlreadyExistsException() {
    }

    public UserWithUsernameAlreadyExistsException(String message) {
        super(message);
    }
}

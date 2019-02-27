package com.witchok.bootnet.exceptions;

import com.witchok.bootnet.domain.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserWithEmailAlreadyExistsException extends UserProcessingException{
    public UserWithEmailAlreadyExistsException() { }

    public UserWithEmailAlreadyExistsException(String message) {
        super(message);
    }

    public UserWithEmailAlreadyExistsException(String message, User user) {
        super(message, user);
    }

}

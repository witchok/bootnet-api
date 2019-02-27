package com.witchok.bootnet.exceptions;

import com.witchok.bootnet.domain.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserWithUsernameAlreadyExistsException extends UserProcessingException{
    public UserWithUsernameAlreadyExistsException() { }

    public UserWithUsernameAlreadyExistsException(String message) {
        super(message);
    }

    public UserWithUsernameAlreadyExistsException(String message, User user) {
        super(message, user);
    }

}


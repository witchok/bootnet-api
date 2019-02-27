package com.witchok.bootnet.exceptions;

import com.witchok.bootnet.domain.users.User;

public abstract class UserProcessingException extends RuntimeException {
    private User user;

    public UserProcessingException() {
    }

    public UserProcessingException(String message) {
        super(message);
    }

    public UserProcessingException(String message, User user){
        super(message);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}

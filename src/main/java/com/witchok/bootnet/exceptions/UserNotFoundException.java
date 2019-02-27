package com.witchok.bootnet.exceptions;


import com.witchok.bootnet.domain.users.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class UserNotFoundException extends UserProcessingException{


    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(String msg, User user){
        super(msg, user);
    }

    public UserNotFoundException(){
        super();
    }

}

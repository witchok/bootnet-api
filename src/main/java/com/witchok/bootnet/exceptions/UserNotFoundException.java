package com.witchok.bootnet.exceptions;

public class UserNotFoundException extends UserProcessingException{


    public UserNotFoundException(String msg){
        super(msg);
    }


    public UserNotFoundException(){
        super();
    }

}

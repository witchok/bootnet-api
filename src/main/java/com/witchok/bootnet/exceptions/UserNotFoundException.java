package com.witchok.bootnet.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,
        value = HttpStatus.NOT_FOUND,
        reason = "User not found")
public class UserNotFoundException extends RuntimeException{
    private String msg;

    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(){
        super();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

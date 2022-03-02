package com.akebabi.backend.security.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.UNAUTHORIZED, reason="Unautorized")
public class UNAuthorizedException extends Exception {

    public UNAuthorizedException(String msg){
        super(msg);
    }
}

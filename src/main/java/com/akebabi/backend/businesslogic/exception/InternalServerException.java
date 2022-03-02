package com.akebabi.backend.businesslogic.exception;

public class InternalServerException extends RuntimeException{

    public InternalServerException(String msg, Exception ex){
        super(msg,ex);
    }

    public InternalServerException(String msg){
        super(msg);
    }
}

package com.akebabi.backend.businesslogic.exception;

import com.akebabi.backend.security.exception.UNAuthorizedException;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log
public class ExceptionHandlerAdvise {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> genericException(Exception e){
        ExceptionResponse response=new ExceptionResponse();
        response.setMessage("Generic exception cause by:"+e.getMessage());
        e.printStackTrace();
        return new ResponseEntity<ExceptionResponse>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UNAuthorizedException.class)
    public ResponseEntity<ExceptionResponse> authenticationException(Exception e){
        ExceptionResponse response=new ExceptionResponse();
        response.setMessage("Authentication Exception:"+e.getMessage());
//        e.printStackTrace();
        return new ResponseEntity<ExceptionResponse>(response,HttpStatus.UNAUTHORIZED);
    }


    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(BadRequestException e){
        ExceptionResponse response=new ExceptionResponse();
        response.setMessage("Cause by:"+e.getMessage());
        response.setStatusCode(HttpStatus.BAD_REQUEST);
//        e.printStackTrace();
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<ExceptionResponse> badRequestException(InternalServerException e){
        ExceptionResponse response=new ExceptionResponse();
        response.setMessage("Cause by:"+e.getMessage());
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
//        e.printStackTrace();
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

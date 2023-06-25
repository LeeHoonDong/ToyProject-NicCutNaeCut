package com.example.backendldh.Exception;

import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomizeResponseEntityException {
    @ExceptionHandler(UrlNotAllowedException.class)
    public final ResponseEntity<Object> handlerUrlNotAllowedException(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), "Fail");
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InterruptedException.class)
    public final ResponseEntity<Object> TryAgainExceptionHandler(Exception ex, WebRequest request){
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(),ex.getMessage(), "Fail");
        return new ResponseEntity<>(exceptionResponse, HttpStatusCode.valueOf(401));
    }
}

package com.example.backendldh.Exception;

public class UrlNotAllowedException extends RuntimeException{
    public UrlNotAllowedException(String message){
        super(message);
    }
}

package com.hw.spring.global.exceptions;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String s){
        super(s);
    }
}

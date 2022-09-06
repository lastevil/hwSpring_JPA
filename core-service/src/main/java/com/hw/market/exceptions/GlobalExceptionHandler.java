package com.hw.market.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<AppError> catchValidateException(ValidateException e){
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }
}

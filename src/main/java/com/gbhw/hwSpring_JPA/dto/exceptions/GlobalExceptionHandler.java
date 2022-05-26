package com.gbhw.hwSpring_JPA.dto.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    public ResponseEntity<AppError> catchValidateException(ValidateException e){
        return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(),e.getMessage()),HttpStatus.BAD_REQUEST);
    }
}

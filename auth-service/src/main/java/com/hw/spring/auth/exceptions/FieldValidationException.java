package com.hw.spring.auth.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FieldValidationException {
    private List<String> errorsStringMessage;

    public FieldValidationException(List<String> errors){
        this.errorsStringMessage=errors;
    }
}

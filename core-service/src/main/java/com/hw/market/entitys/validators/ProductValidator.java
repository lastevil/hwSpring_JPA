package com.hw.market.entitys.validators;

import com.hw.spring.global.dto.ProductDto;
import com.hw.spring.global.exceptions.ValidateException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductValidator {
    public void validate(ProductDto productDto){
        List<String> errors = new ArrayList<>();

        if (productDto.getPrice()<1){
            errors.add("Цена не может быть меньше 1");
        }
        if (productDto.getTitle().isBlank()){
            errors.add("Название не может быть пустым");
        }
        if (!errors.isEmpty()){
            throw new ValidateException(errors);
        }
    }
}

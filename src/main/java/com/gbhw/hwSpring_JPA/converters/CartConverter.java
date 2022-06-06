package com.gbhw.hwSpring_JPA.converters;

import com.gbhw.hwSpring_JPA.dto.CartDto;
import com.gbhw.hwSpring_JPA.dto.OrderDto;
import com.gbhw.hwSpring_JPA.dto.ProductDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CartConverter {
    public List<CartDto> productDtoListToCartDtoList(List<ProductDto> product) {
        Map<ProductDto, Integer> products = product.stream().collect(Collectors.toMap(
                x -> x, value -> 1, Integer::sum
        ));
        ArrayList<CartDto> a = new ArrayList<>();
        for (Map.Entry<ProductDto, Integer> entery : products.entrySet()) {
            a.add(new CartDto(
                    entery.getKey().getId(),
                    entery.getKey().getTitle(),
                    entery.getKey().getCoast(),
                    entery.getValue()
            ));
        }
        return a;
    }
}

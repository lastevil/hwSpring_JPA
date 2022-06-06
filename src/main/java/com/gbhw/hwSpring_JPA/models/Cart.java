package com.gbhw.hwSpring_JPA.models;


import com.gbhw.hwSpring_JPA.dto.CartDto;
import com.gbhw.hwSpring_JPA.dto.ProductDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Data
@Component
@NoArgsConstructor
public class Cart {

    List<ProductDto> products = new ArrayList<>();

    public void addProduct(ProductDto product) {
        products.add(product);
    }

    public void removeProduct(ProductDto product) {
        products.remove(product);
    }

    public void removeAllProductsById(ProductDto productDto) {
        List<ProductDto> removeList = new ArrayList<>();
        for (ProductDto p : products) {
            if (p.equals(productDto)) {
                removeList.add(p);
            }
        }
        products.removeAll(removeList);
    }

    public Integer getSum() {
        Integer sum=0;
        for (ProductDto p: products) {
            sum+=p.getCoast();
        }
        return sum;
    }
}

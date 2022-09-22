package com.hw.spring.cart.converters;

import com.hw.constans.dto.CartDto;
import com.hw.spring.cart.models.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {

    public CartDto fromCart(Cart cart){
        CartDto cartDto = new CartDto();
        cartDto.setProducts(cart.getProducts());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setTotalProductsCount(cart.getTotalProductsCount());
        return cartDto;
    }
}

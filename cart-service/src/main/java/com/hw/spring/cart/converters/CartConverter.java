package com.hw.spring.cart.converters;

import com.hw.spring.cart.models.Cart;
import com.hw.spring.global.dto.CartDto;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {
    public CartDto fromCart(Cart cart){
        CartDto cartDto = new CartDto();
        cartDto.setProducts(cart.getProducts());
        cartDto.setTotalPrice(cartDto.getTotalPrice());
        cartDto.setTotalProductsCount(cart.getTotalProductsCount());
        return cartDto;
    }
}

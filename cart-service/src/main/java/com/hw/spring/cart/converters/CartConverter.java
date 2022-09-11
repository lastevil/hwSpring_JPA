package com.hw.spring.cart.converters;

import com.hw.spring.cart.dto.CartDto;
import com.hw.spring.cart.dto.OrderDetailsDto;
import com.hw.spring.cart.models.Cart;
import org.springframework.stereotype.Component;

@Component
public class CartConverter {
    public CartDto fromCart(Cart cart, OrderDetailsDto detailsDto, String username){
        CartDto cartDto = new CartDto();
        cartDto.setProducts(cart.getProducts());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setTotalProductsCount(cart.getTotalProductsCount());
        cartDto.setAddressId(detailsDto.getAddressId());
        cartDto.setPhone(detailsDto.getPhone());
        cartDto.setUsername(username);
        return cartDto;
    }

    public CartDto fromCart(Cart cart){
        CartDto cartDto = new CartDto();
        cartDto.setProducts(cart.getProducts());
        cartDto.setTotalPrice(cart.getTotalPrice());
        cartDto.setTotalProductsCount(cart.getTotalProductsCount());
        cartDto.setAddressId(null);
        cartDto.setPhone(null);
        cartDto.setUsername(null);
        return cartDto;
    }
}

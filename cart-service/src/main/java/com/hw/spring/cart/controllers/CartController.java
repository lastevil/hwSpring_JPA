package com.hw.spring.cart.controllers;

import com.hw.spring.cart.converters.CartConverter;
import com.hw.spring.cart.models.Cart;
import com.hw.spring.cart.services.CartService;
import com.hw.spring.global.dto.CartDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService service;
    private final CartConverter cartConverter;

    @PostMapping
    public CartDto getCurrentCart(@RequestBody String cartName) {
        log.info(cartName);
        return cartConverter.fromCart(service.getCurrentCart(cartName));

    }

    @PostMapping("/{id}")
    public void addProduct(@PathVariable Long id, @RequestBody String cartName) {
        service.addProductByIdToCart(id, cartName);
    }

    @PostMapping("/remove/{id}")
    public void removeProduct(@PathVariable Long id, @RequestBody String cartName) {
        service.removeOneProductByIdFromCart(id, cartName);
    }

    @PostMapping("/removeAll/{id}")
    public void removeAllProduct(@PathVariable Long id, @RequestBody String cartName) {
        service.removeAllProductByIdFromCart(id, cartName);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestBody String cartName) {
        service.clearCart(cartName);
    }

    @PostMapping("/productCount")
    public Integer getProductCountInCart(@RequestBody String cartName) {
        return service.getCurrentCart(cartName).getTotalProductsCount();
    }
}
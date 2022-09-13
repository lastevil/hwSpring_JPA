package com.hw.spring.cart.controllers;


import com.hw.constans.dto.CartDto;
import com.hw.spring.cart.converters.CartConverter;
import com.hw.spring.cart.dto.OrderDetailsDto;
import com.hw.spring.cart.services.CartService;
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

    @PostMapping("/createOrder/{cartName}")
    public void createOrder(@PathVariable String cartName, @RequestBody OrderDetailsDto orderDetailsDto, @RequestHeader String username) {
        service.createOrder(orderDetailsDto,"{\"cartName\":\""+cartName+"\"}",username);
    }

}
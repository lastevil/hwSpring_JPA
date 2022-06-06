package com.gbhw.hwSpring_JPA.controllers;

import com.gbhw.hwSpring_JPA.dto.CartDto;
import com.gbhw.hwSpring_JPA.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartService cartService;

    @GetMapping("")
    public @ResponseBody List<CartDto> getProductListInCartById() {
        return cartService.getCartList();
    }
    @GetMapping("/count")
    public @ResponseBody Integer getCartCount() {
        return cartService.getCartCount();
    }

    @GetMapping("/sum")
    public Integer getCartSum() {
        return cartService.getCartSum();
    }

    @PostMapping("/{productId}")
    public void toCart(@PathVariable Long productId) {
        cartService.toCart(productId);
    }

    @DeleteMapping("/{productId}")
    public void delFromCart(@PathVariable Long productId) {
        cartService.deleteProductFromCart(productId);
    }

    @DeleteMapping("/delAll/{productId}")
    public void deleteAllFromCartById(@PathVariable Long productId) {
        cartService.deleteProductListFromCart(productId);
    }
}

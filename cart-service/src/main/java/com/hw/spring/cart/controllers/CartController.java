package com.hw.spring.cart.controllers;


import com.hw.constans.dto.CartDto;
import com.hw.spring.cart.converters.CartConverter;
import com.hw.spring.cart.dto.OrderDetailsDto;
import com.hw.spring.cart.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
@Slf4j
@Tag(name="Корзина",description = "Контроллер работы с корзиной")
public class CartController {
    private final CartService service;
    private final CartConverter cartConverter;
    @Operation(summary = "метод получения корзины пользователя")
    @PostMapping
    public CartDto getCurrentCart(@RequestBody String cartName) {
        return cartConverter.fromCart(service.getCurrentCart(cartName));
    }
    @Operation(summary = "метод добавления продукта в корзину")
    @PostMapping("/{id}")
    public void addProduct(@PathVariable Long id, @RequestBody String cartName) {
        service.addProductByIdToCart(id, cartName);
    }
    @Operation(summary = "метод удаления продукта из корзины")
    @PostMapping("/remove/{id}")
    public void removeProduct(@PathVariable Long id, @RequestBody String cartName) {
        service.removeOneProductByIdFromCart(id, cartName);
    }
    @Operation(summary = "метод удаления всех продуктов одного вида (с одним id)")
    @PostMapping("/removeAll/{id}")
    public void removeAllProduct(@PathVariable Long id, @RequestBody String cartName) {
        service.removeAllProductByIdFromCart(id, cartName);
    }
    @Operation(summary = "метод очистки корзины")
    @PostMapping("/clear")
    public void clearCart(@RequestBody String cartName) {
        service.clearCart(cartName);
    }
    @Operation(summary = "метод получения колличества продуктов в корзине")
    @PostMapping("/productCount")
    public Integer getProductCountInCart(@RequestBody String cartName) {
        return service.getCurrentCart(cartName).getTotalProductsCount();
    }
    @Operation(summary = "метод создания заказа из текущей корзины")
    @PostMapping("/createOrder/{cartName}")
    public void createOrder(@PathVariable String cartName, @RequestBody OrderDetailsDto orderDetailsDto, @RequestHeader String username) {
        service.createOrder(orderDetailsDto,"{\"cartName\":\""+cartName+"\"}",username);
    }

}
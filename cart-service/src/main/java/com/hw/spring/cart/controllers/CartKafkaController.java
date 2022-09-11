package com.hw.spring.cart.controllers;

import com.hw.spring.cart.converters.CartConverter;
import com.hw.spring.cart.dto.CartDto;
import com.hw.spring.cart.dto.OrderDetailsDto;
import com.hw.spring.cart.models.Cart;
import com.hw.spring.cart.services.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/createOrder")
public class CartKafkaController {
    @Autowired
    CartService cartService;
    @Autowired
    CartConverter cartConverter;
    @Qualifier(value = "KafkaTest")
    @Autowired
    KafkaTemplate<Long, CartDto> template;

    @PostMapping("/{cartName}")
    public void createOrder(@PathVariable String cartName, @RequestBody OrderDetailsDto orderDetailsDto, @RequestHeader String username) {
        log.info(cartName);
        log.info(orderDetailsDto.getPhone()+"  "+orderDetailsDto.getAddressId());
        log.info(username);
        Cart currentCart = cartService.getCurrentCart(cartName);
        CartDto cartDto = cartConverter.fromCart( currentCart, orderDetailsDto, username);
        template.send("Cart", cartDto);
                cartService.clearCart(cartName);
    }
}

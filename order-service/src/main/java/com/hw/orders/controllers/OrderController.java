package com.hw.orders.controllers;

import com.hw.orders.services.AddressService;
import com.hw.orders.services.OrderService;
import com.hw.spring.global.dto.AddressDto;
import com.hw.spring.global.dto.OrderDetailsDto;
import com.hw.orders.dto.OrderDto;
import com.hw.spring.global.dto.OrderItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final AddressService addressService;

    @PostMapping("/addAddress")
    public void addAddress(@RequestBody AddressDto address, @RequestHeader String username){
        addressService.addAddress(address,username);
    }

    @DeleteMapping("/address/{id}")
    public void deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
    }

    @GetMapping("/addresses")
    public List<AddressDto> getAddresses(@RequestHeader String username){
        return addressService.getAddresses(username);
    }

    @GetMapping()
    public List<OrderDto> getOrders(@RequestHeader String username){
        return orderService.getUserOrders(username);
    }

    @PostMapping("/pay/{id}")
    public void payOrder(@PathVariable Long id){
        orderService.payOrder(id);
    }
    @GetMapping("/{id}")
    public List<OrderItemDto> getOrderItems(@PathVariable Long id){
        return orderService.getOrderItems(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
            orderService.deleteOrderById(id);
    }

    @PostMapping("/{cartName}")
    public void addOrder(@PathVariable String cartName, @RequestBody OrderDetailsDto orderDetailsDto, @RequestHeader String username){
        orderService.createOrder(orderDetailsDto, cartName, username);
    }
}

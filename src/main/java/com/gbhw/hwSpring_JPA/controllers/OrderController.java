package com.gbhw.hwSpring_JPA.controllers;

import com.gbhw.hwSpring_JPA.dto.AddressDto;
import com.gbhw.hwSpring_JPA.dto.OrderDetailsDto;
import com.gbhw.hwSpring_JPA.dto.OrderDto;
import com.gbhw.hwSpring_JPA.dto.OrderItemDto;
import com.gbhw.hwSpring_JPA.services.AddressService;
import com.gbhw.hwSpring_JPA.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.web.servlet.headers.HeadersSecurityMarker;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;
    private final AddressService addressService;

    @PostMapping("/addAddress")
    public void addAddress(@RequestBody AddressDto address, @HeadersSecurityMarker UsernamePasswordAuthenticationToken token){
        addressService.addAddress(address,token.getPrincipal().toString());
    }

    @DeleteMapping("/address/{id}")
    public void deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
    }

    @GetMapping("/addresses")
    public List<AddressDto> getAddresses(@HeadersSecurityMarker UsernamePasswordAuthenticationToken token){
        return addressService.getAddresses(token.getPrincipal().toString());
    }

    @GetMapping()
    public List<OrderDto> getOrders(@HeadersSecurityMarker UsernamePasswordAuthenticationToken token){
        return orderService.getUserOrders(token.getPrincipal().toString());
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
    public void addOrder(@PathVariable String cartName, @RequestBody OrderDetailsDto orderDetailsDto, @HeadersSecurityMarker UsernamePasswordAuthenticationToken token){
        orderService.createOrder(orderDetailsDto, cartName, token.getPrincipal().toString());
    }
}

package com.hw.orders.controllers;

import com.hw.constans.dto.OrderItemDto;
import com.hw.orders.services.AddressService;
import com.hw.orders.services.OrderService;
import com.hw.orders.dto.AddressDto;
import com.hw.orders.dto.OrderDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
@Tag(name = "Заказы", description = "Контроллер обработки заказов")
public class OrderController {

    private final OrderService orderService;
    private final AddressService addressService;
    @Operation(summary = "метод добавления нового адреса в базу")
    @PostMapping("/addAddress")
    public void addAddress(@RequestBody AddressDto address, @RequestHeader String username){
        addressService.addAddress(address,username);
    }
    @Operation(summary = "метод удаления адреса из базы")
    @DeleteMapping("/address/{id}")
    public void deleteAddress(@PathVariable Long id){
        addressService.deleteAddress(id);
    }
    @Operation(summary = "метод получения списка всех адресов заказчика")
    @GetMapping("/addresses")
    public List<AddressDto> getAddresses(@RequestHeader String username){
        return addressService.getAddresses(username);
    }
    @Operation(summary = "метод получения списка заказов")
    @GetMapping()
    public List<OrderDto> getOrders(@RequestHeader String username){
        return orderService.getUserOrders(username);
    }

    @Operation(summary = "метод получения продуктов заказа")
    @GetMapping("/{id}")
    public List<OrderItemDto> getOrderItems(@PathVariable Long id){
        return orderService.getOrderItems(id);
    }

    @Operation(summary = "метод удаления заказа из базы")
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id){
            orderService.deleteOrderById(id);
    }
}

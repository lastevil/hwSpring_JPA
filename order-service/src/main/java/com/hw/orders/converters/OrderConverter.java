package com.hw.orders.converters;

import com.hw.orders.entitys.Order;
import com.hw.orders.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class OrderConverter {
    private AddressConverter addressConverter;
    public OrderDto toOrderDto(Order order){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        OrderDto orderDto = new OrderDto(
                order.getId(),
                order.getTotalPrice(),
                order.getCreated_at().format(formatter),
                order.getOrderStatus(),
                addressConverter.fromEntity(order.getAddress()));
        return orderDto;
    }
}

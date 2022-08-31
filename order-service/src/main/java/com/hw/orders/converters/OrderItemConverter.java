package com.hw.orders.converters;

import com.hw.orders.entitys.OrderItem;
import com.hw.spring.global.dto.OrderItemDto;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {
    public OrderItemDto toOrderItemDto(OrderItem orderItem){
        OrderItemDto dto = new OrderItemDto();
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());
        dto.setTitle(orderItem.getProductTitle());
        dto.setPricePerProduct(orderItem.getPricePerProduct());
        return dto;
    }
}

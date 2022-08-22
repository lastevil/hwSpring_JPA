package com.gbhw.hwSpring_JPA.converters;

import com.gbhw.hwSpring_JPA.dto.OrderItemDto;
import com.gbhw.hwSpring_JPA.entitys.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemConverter {
    public OrderItemDto toOrderItemDto(OrderItem orderItem){
        OrderItemDto dto = new OrderItemDto();
        dto.setQuantity(orderItem.getQuantity());
        dto.setPrice(orderItem.getPrice());
        dto.setTitle(orderItem.getProduct().getTitle());
        dto.setProductId(orderItem.getProduct().getId());
        dto.setPricePerProduct(orderItem.getPricePerProduct());
        return dto;
    }
}

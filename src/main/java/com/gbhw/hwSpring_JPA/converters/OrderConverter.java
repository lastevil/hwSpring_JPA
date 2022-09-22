package com.gbhw.hwSpring_JPA.converters;

import com.gbhw.hwSpring_JPA.dto.OrderDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import com.gbhw.hwSpring_JPA.entitys.Order;

import java.time.format.DateTimeFormatter;

@Component
@AllArgsConstructor
public class OrderConverter {
    private AddressConverter addressConverter;
    public OrderDto toOrderDto(Order order){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        OrderDto orderDto = new OrderDto(order.getId(),
                order.getTotalPrice(),
                order.getCreated_at().format(formatter),
                order.getOrderStatus(),
                addressConverter.fromEntity(order.getAddress()));
        return orderDto;
    }
}

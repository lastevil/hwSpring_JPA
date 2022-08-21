package com.gbhw.hwSpring_JPA.dto;

import com.gbhw.hwSpring_JPA.entitys.Address;
import com.gbhw.hwSpring_JPA.entitys.OrderItem;
import com.gbhw.hwSpring_JPA.entitys.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private List<OrderItem> items;
    private Integer total_price;
    private LocalDateTime created_at;
    private OrderStatus orderStatus;
    private AddressDto address;
}

package com.hw.orders.dto;



import com.hw.orders.entitys.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    //private List<OrderItem> items;
    private Integer totalPrice;
    private String created_at;
    private OrderStatus orderStatus;
    private AddressDto address;
}

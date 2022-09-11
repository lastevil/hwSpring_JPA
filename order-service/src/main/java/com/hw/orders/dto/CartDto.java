package com.hw.orders.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    private List<OrderItemDto> products;
    private Integer totalProductsCount;
    private Integer totalPrice;
    private String username;
    private String phone;
    private Long addressId;
}

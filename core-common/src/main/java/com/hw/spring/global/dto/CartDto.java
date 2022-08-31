package com.hw.spring.global.dto;

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
}

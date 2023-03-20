package com.hw.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    @Schema(description = "Список продуктов")
    private List<OrderItemDto> products;
    @Schema(description = "Колличество продуктов",example = "20")
    private Integer totalProductsCount;
    @Schema(description = "Общая цена продуктов", example = "100")
    private Integer totalPrice;
    @Schema(description = "Login заказчика",example = "bob")
    private String username;
    @Schema(description = "Телефон заказчика", example = "+72848234823")
    private String phone;
    @Schema(description = "Id адреса заказчика", example = "1")
    private Long addressId;
}

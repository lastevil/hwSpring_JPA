package com.hw.orders.dto;



import com.hw.orders.entitys.OrderItem;
import com.hw.orders.entitys.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    @Schema(description = "Цена заказа",example = "100")
    private Integer totalPrice;
    @Schema(description = "Дата создания заказа",example = "22.07.2022")
    private String created_at;
    @Schema(description = "Статус заказа")
    private OrderStatus orderStatus;
    @Schema(description = "Адрес заказа")
    private AddressDto address;
}

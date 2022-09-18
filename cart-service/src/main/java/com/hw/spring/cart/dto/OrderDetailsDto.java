package com.hw.spring.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailsDto {
    @Schema(description = "Телефон заказчика",example = "+720920394820")
    private String phone;
    @Schema(description = "ID адреса заказчика", example = "1")
    private Long addressId;
}

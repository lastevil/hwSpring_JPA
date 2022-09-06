package com.hw.orders.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDetailsDto {
    private String phone;
    private Long addressId;
}

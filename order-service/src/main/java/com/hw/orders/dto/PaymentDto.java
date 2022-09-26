package com.hw.orders.dto;

import lombok.Data;

@Data
public class PaymentDto {
    Long orderId;
    String billId;
}

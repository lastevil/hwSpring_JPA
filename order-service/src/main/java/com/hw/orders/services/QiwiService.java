package com.hw.orders.services;

import com.hw.orders.api.AuthApi;
import com.hw.orders.dto.UserDto;
import com.hw.orders.entitys.Order;
import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CreateBillInfo;
import com.qiwi.billpayments.sdk.model.in.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QiwiService {
    private final OrderService orderService;
    private final AuthApi authApi;
    public CreateBillInfo createOrderRequest(Long orderId){
        Order order = orderService.findById(orderId);
        UserDto userDto = authApi.getUserByUsername(order.getUsername());
        CreateBillInfo billInfo = new CreateBillInfo(
                UUID.randomUUID().toString(),
                new MoneyAmount(
                        BigDecimal.valueOf(order.getTotalPrice()),
                        Currency.getInstance("RUB")
                ),
                orderId.toString(),
                ZonedDateTime.now().plusDays(45),
                new Customer(
                        userDto.getEmail(),
                        UUID.randomUUID().toString(),
                        order.getPhone()
                ),
                "http://localhost:3000/front/#!/order"
        );
        return billInfo;
    }

}

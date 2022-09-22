package com.hw.orders.services;

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

    public CreateBillInfo createOrderRequest(Long orderId){
        Order order = orderService.findById(orderId);

        CreateBillInfo billInfo = new CreateBillInfo(
                UUID.randomUUID().toString(),
                new MoneyAmount(
                        BigDecimal.valueOf(1),
                        Currency.getInstance("RUB")
                ),
                "comment",
                ZonedDateTime.now().plusDays(45),
                new Customer(
                        "mail@example.org",
                        UUID.randomUUID().toString(),
                        "79123456789"
                ),
                "http://localhost:3000/front/#!/store"
        );
        return billInfo;
    }

}

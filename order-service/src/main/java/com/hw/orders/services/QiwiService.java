package com.hw.orders.services;

import com.hw.orders.api.AuthApi;
import com.hw.orders.api.QiwiApi;
import com.hw.orders.dto.QiwiBillResponse;
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

    private final QiwiApi qiwiApi;
    public CreateBillInfo createOrderRequest(Long orderId){
        Order order = orderService.findById(orderId);
        UserDto userDto = authApi.getUserByUsername(order.getUsername());
        StringBuilder sb = new StringBuilder();
        order.getItems().stream().forEach(item-> sb.append(item.getProductTitle()+" x "+item.getQuantity()+" = "+item.getPrice()+"\n"));
        CreateBillInfo billInfo = new CreateBillInfo(
                UUID.randomUUID().toString(),
                new MoneyAmount(
                        BigDecimal.valueOf(1), //order.getTotalPrice()
                        Currency.getInstance("RUB")
                ),
                "Ваш заказ:\n"+
                sb.toString(),
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

    public QiwiBillResponse getBillInfo(String billId, String header){
        QiwiBillResponse response = qiwiApi.getUserByUsername(billId, header);
        return response;
    }

}

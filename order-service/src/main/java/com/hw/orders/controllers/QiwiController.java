package com.hw.orders.controllers;

import com.hw.orders.dto.QiwiResponse;
import com.hw.orders.services.QiwiService;
import com.qiwi.billpayments.sdk.client.BillPaymentClient;
import com.qiwi.billpayments.sdk.client.BillPaymentClientFactory;
import com.qiwi.billpayments.sdk.model.out.BillResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/qiwi")
@RequiredArgsConstructor
@Slf4j
@PropertySource("classpath:secret.properties")
public class QiwiController {
    @Value("${qiwi.secret}")
    private String secret;
    @Value("${qiwi.public}")
    private String pubId;
    private BillPaymentClient client = BillPaymentClientFactory.createDefault(secret);

    private final QiwiService qiwiService;

    //Добавить статусы "Оплачен\не оплачен" в наш Ордер
    // В зависимости от успеха операции, менять этот статус
    // Оплаченные заказы не долны быть оплачены дважды

    @GetMapping("/create/{orderId}")
    public QiwiResponse createOrder(@PathVariable Long orderId) throws URISyntaxException {
        BillResponse response = client.createBill(qiwiService.createOrderRequest(orderId));
        log.info("resp = {}", response);
        return new QiwiResponse(response.getPayUrl(), response.getBillId());
    }

    @PostMapping("/capture/{billId}")
    public ResponseEntity<?> captureOrder(@PathVariable String billId) throws IOException {
        BillResponse response = client.getBillInfo(billId);
        if("COMPLETED".equals(response.getStatus())) {
            //ToDo сделать обработку статуса
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

package com.hw.orders.dto;

import com.qiwi.billpayments.sdk.model.MoneyAmount;
import com.qiwi.billpayments.sdk.model.in.CustomFields;
import com.qiwi.billpayments.sdk.model.in.Customer;
import com.qiwi.billpayments.sdk.model.out.ResponseStatus;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class QiwiBillResponse {
    private String siteId;
    private String billId;
    private MoneyAmount amount;
    private ResponseStatus status;
    private String comment;
    private Customer customer;
    private ZonedDateTime creationDateTime;
    private ZonedDateTime expirationDateTime;
    private String payUrl;
    private CustomFields customFields;
    private String recipientPhoneNumber;
}

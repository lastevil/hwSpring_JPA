package com.hw.orders.api;

import com.hw.orders.configurations.FeignConfig;
import com.hw.orders.dto.QiwiBillResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "PyaInfo",url = "https://api.qiwi.com/partner/bill/v1/bills",configuration = FeignConfig.class)
public interface QiwiApi {
    @RequestMapping(method = RequestMethod.GET, value = "/{billId}")
    QiwiBillResponse getUserByUsername(@PathVariable(value = "billId") String billId ,@RequestHeader("Authorization") String header);
}

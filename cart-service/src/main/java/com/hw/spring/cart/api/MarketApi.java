package com.hw.spring.cart.api;

import com.hw.constans.dto.ProductDto;
import com.hw.spring.cart.configurations.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
@FeignClient(value = "market-service/market/api/v1/products", configuration = FeignConfig.class)
public interface MarketApi {
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    ProductDto getProductById(@PathVariable Long id);
}

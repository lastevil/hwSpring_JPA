package com.hw.orders.api;

import com.hw.orders.configurations.FeignConfig;
import com.hw.orders.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "auth-service/auth/api/v1/", configuration = FeignConfig.class)
public interface AuthApi {
    @RequestMapping(method = RequestMethod.GET, value = "userInfo/{username}")
    UserDto getUserByUsername(@PathVariable(value = "username") String username);
}

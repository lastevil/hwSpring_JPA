package com.hw.spring.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CartServiceApp {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceApp.class, args);
    }
}

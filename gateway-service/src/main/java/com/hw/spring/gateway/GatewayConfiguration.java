package com.hw.spring.gateway;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableEurekaClient
@PropertySource("classpath:secret.properties")
public class GatewayConfiguration {
}

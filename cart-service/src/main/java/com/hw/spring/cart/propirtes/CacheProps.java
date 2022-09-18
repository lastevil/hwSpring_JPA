package com.hw.spring.cart.propirtes;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.cache.user")
@Data
public class CacheProps {
    private Long expireTime;
    private String name;
}

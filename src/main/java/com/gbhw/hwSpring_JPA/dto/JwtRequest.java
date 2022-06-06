package com.gbhw.hwSpring_JPA.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}

package com.hw.spring.auth.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String username;

    private String password;

    private String email;

    private LocalDateTime createdAt;

    public UserDto(String username,String password,String email){
        this.username=username;
        this.password=password;
        this.email=email;
    }
}

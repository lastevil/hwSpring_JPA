package com.hw.spring.auth.dto;


import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "id пользователя",example = "46")
    private Long id;
    @Schema(description = "Имя пользователя", example = "Bob")
    private String username;
    @Schema(description = "Пароль", example = "Qwe12345!")
    private String password;
    @Schema(description = "почта", example = "example@mail.ru")
    private String email;
    @Schema(description = "время создания")
    private LocalDateTime createdAt;

    public UserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}

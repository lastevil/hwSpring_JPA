package com.hw.orders.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    @Schema(description = "id пользователя",example = "46")
    private Long id;
    @Schema(description = "Имя пользователя", example = "Bob")
    private String username;
    @Schema(description = "почта", example = "example@mail.ru")
    private String email;
    @Schema(description = "время создания")
    private LocalDateTime createdAt;

}

package com.hw.constans.exceptoins;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppError {
    private int statusCode;
    private String message;
}

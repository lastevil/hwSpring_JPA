package com.hw.constans.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    @Schema(description = "Наименование продукта",example = "печенье")
    private String title;
    @Schema(description = "Цена продукта", example = "50")
    private Integer price;
}

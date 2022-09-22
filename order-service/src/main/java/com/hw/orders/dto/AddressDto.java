package com.hw.orders.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    @Schema(description = "Страна",example = "Россия")
    private String country;
    @Schema(description = "Город", example = "Нововыдумск")
    private String city;
    @Schema(description = "Улица",example = "Примерная")
    private String street;
    @Schema(description = "номер дома",example = "22")
    private Integer house;
    @Schema(description = "номер квартиры",example = "33")
    private Integer flat;
    @Schema(description = "почтовый индекс",example = "234134")
    private Integer postIndex;
    @Schema(description = "полный адрес (строка из предыдущих заполненых полей)",
            example = "234134, Россия, Нововыдумск, ул.Примерная, дом 22, кв. 33")
    private String fullAddress;
}

package com.hw.orders.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String country;
    private String city;
    private String street;
    private Integer house;
    private Integer flat;
    private Integer postIndex;
    private String fullAddress;
}

package com.gbhw.hwSpring_JPA.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartDto {
    Long id;
    String title;
    Integer coast;
    Integer count;
}

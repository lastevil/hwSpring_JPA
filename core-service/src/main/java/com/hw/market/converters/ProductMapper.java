package com.hw.market.converters;

import com.hw.market.entitys.Product;
import com.hw.spring.global.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto productDto);
    ProductDto toProductDTO(Product product);
}

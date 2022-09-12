package com.hw.market.converters;


import com.hw.constans.dto.ProductDto;
import com.hw.market.entitys.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

    Product toProduct(ProductDto productDto);
    @InheritInverseConfiguration
    ProductDto toProductDTO(Product product);
}

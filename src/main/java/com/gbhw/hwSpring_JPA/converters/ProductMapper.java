package com.gbhw.hwSpring_JPA.converters;

import com.gbhw.hwSpring_JPA.dto.ProductDto;
import com.gbhw.hwSpring_JPA.models.Product;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

// @Mapper - с этой аннотацией ошибка
public interface ProductMapper {
    ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);
    @InheritInverseConfiguration
    public Product toProduct(ProductDto productDto);

    public ProductDto fromProduct(Product product);

   public Page<ProductDto> fromProductPage(Page<Product> productPage);
}

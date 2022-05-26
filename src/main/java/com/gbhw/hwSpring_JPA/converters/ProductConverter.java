package com.gbhw.hwSpring_JPA.converters;

import com.gbhw.hwSpring_JPA.dto.ProductDto;
import com.gbhw.hwSpring_JPA.models.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public Product dtoToEntity(ProductDto productDto) {
        return new Product(productDto.getId(), productDto.getTitle(), productDto.getCoast());
    }

    public ProductDto entityToDto(Product product) {
        return new ProductDto(product.getId(), product.getTitle(), product.getCoast());
    }
}

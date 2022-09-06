package com.hw.market.services;


import com.hw.market.converters.ProductMapper;
import com.hw.market.dto.ProductDto;
import com.hw.market.entitys.Product;
import com.hw.market.entitys.validators.ProductValidator;
import com.hw.market.exceptions.ResourceNotFoundException;
import com.hw.market.exceptions.ValidateException;
import com.hw.market.repositorys.ProductRepository;
import com.hw.market.repositorys.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductValidator productValidator;

    public ProductDto getProductById(Long id) {
        Product p = productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Продукт не найден"));
        return productMapper.toProductDTO(p);
    }

    @Transactional
    public void addProduct(ProductDto productDto) {
        productValidator.validate(productDto);
        Product a = productMapper.toProduct(productDto);
        productRepository.save(a);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<ProductDto> getAllProducts(Integer page, Integer min, Integer max) {
        Specification<Product> productSpecification = Specification.where(null);
        if (min != null) {
            productSpecification = productSpecification.and(ProductSpecification.coastGreaterThenOrElseThen(min));
        }
        if (max != null) {
            productSpecification = productSpecification.and(ProductSpecification.coastLessThenOrElseThen(max));
        }
            return productRepository.findAll(productSpecification, PageRequest.of(page - 1, 10))
                .map(productMapper::toProductDTO);
    }

    @Transactional
    public void updateProduct(ProductDto productDto) {
        if (!productRepository.existsProductById(productDto.getId())) {
            throw new ValidateException(List.of("Продукта не существует"));
        }
        productValidator.validate(productDto);
        Product product = productRepository.getById(productDto.getId());
        product.setPrice(productDto.getPrice());
        product.setTitle(product.getTitle());

    }
    public List<Product> getAllProductList() {
        List<ProductDto> productList = new ArrayList<>();
        return productRepository.findAll();
    }
}

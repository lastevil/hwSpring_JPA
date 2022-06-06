package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.converters.ProductConverter;
import com.gbhw.hwSpring_JPA.dto.ProductDto;
import com.gbhw.hwSpring_JPA.dto.exceptions.ValidateException;
import com.gbhw.hwSpring_JPA.models.Product;
import com.gbhw.hwSpring_JPA.repositorys.ProductRepository;
import com.gbhw.hwSpring_JPA.repositorys.specification.ProductSpecification;
import com.gbhw.hwSpring_JPA.validators.ProductValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final ProductValidator productValidator;

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id).map(productConverter::entityToDto).orElseThrow();
    }

    @Transactional
    public void addProduct(ProductDto productDto) {
        productValidator.validate(productDto);
        Product a = productConverter.dtoToEntity(productDto);
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
        //Возвращает Null в полях объектов
        //return productRepository.findAll(productSpecification,PageRequest.of(page-1,10)).map(ProductMapper.MAPPER::fromProduct);
        return productRepository.findAll(productSpecification, PageRequest.of(page - 1, 10))
                .map((productConverter::entityToDto));
    }

    @Transactional
    public void updateProduct(ProductDto productDto) {
        if (!productRepository.existsProductById(productDto.getId())) {
            throw new ValidateException(List.of("Продукта не существует"));
        }
        productValidator.validate(productDto);
        Product product = productRepository.getById(productDto.getId());
        product.setCoast(productDto.getCoast());
        product.setTitle(product.getTitle());

    }
    //Вопрос: к какому сервису это относится?
    public List<ProductDto> getProductDTOList(List<Long> orderList) {
        List<ProductDto> productList = new ArrayList<>();
        if (orderList.isEmpty()) {
            return productList;
        }
        for (Long i:orderList) {
            productList.add(productConverter.entityToDto(productRepository.findById(i).orElseThrow()));
        }
        return productList;
    }
}

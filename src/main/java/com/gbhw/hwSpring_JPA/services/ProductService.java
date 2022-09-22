package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.models.Product;
import com.gbhw.hwSpring_JPA.repositorys.ProductRepository;
import com.gbhw.hwSpring_JPA.repositorys.specification.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public void addProduct(String title, Integer coast) {
        Product a = new Product();
        a.setCoast(coast);
        a.setTitle(title);
        productRepository.save(a);
    }

    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> getAllProducts(Integer page, Integer min, Integer max) {
        Specification<Product> productSpecification = Specification.where(null);
        if (min != null) {
            productSpecification = productSpecification.and(ProductSpecification.coastGreaterThenOrElseThen(min));
        }
        if (max != null) {
            productSpecification = productSpecification.and(ProductSpecification.coastLessThenOrElseThen(max));
        }
        return productRepository.findAll(productSpecification, PageRequest.of(page-1, 10));
    }
}

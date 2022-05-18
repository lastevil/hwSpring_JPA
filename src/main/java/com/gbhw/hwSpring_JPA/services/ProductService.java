package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.models.Product;
import com.gbhw.hwSpring_JPA.repositorys.ProductRepository;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
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

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsBetweenCoast(Integer min, Integer max) {
        return productRepository.findAllBetweenCoast(min,max);
    }

    public List<Product> getProductsMoreThenMin(Integer min) {
        return productRepository.getProductsMoreThenMin(min);
    }

    public List<Product> getProductsLessThenMax(Integer max) {
        return productRepository.getProductsLessThenMax(max);
    }
}

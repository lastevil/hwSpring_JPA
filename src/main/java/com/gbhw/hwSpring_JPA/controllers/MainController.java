package com.gbhw.hwSpring_JPA.controllers;

import com.gbhw.hwSpring_JPA.models.Product;
import com.gbhw.hwSpring_JPA.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    ProductService productService;
    private Integer pageCount = 0;
    @GetMapping("/products/{id}")
    public @ResponseBody Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(@RequestParam("page") Integer page) {
        PageRequest pageRequest = PageRequest.of(page, 10);
        Page<Product> productPage = productService.getAllProducts(pageRequest);
        pageCount = productPage.getTotalPages();
        return productPage.getContent();
    }
    @GetMapping("/getPagesCount")
    public Integer getPageCount(){
        return pageCount;
    }

    @PostMapping("/products")
    public void addProduct(@RequestParam String title, @RequestParam Integer coast) {
        productService.addProduct(title, coast);
    }

    @GetMapping("/products/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }

    @GetMapping("/products/findBetween")
    public List<Product> getProductsBetween(@RequestParam(defaultValue = "0") Integer min, @RequestParam(defaultValue = "10000000") Integer max) {
        return productService.getProductsBetweenCoast(min, max);
    }
    @GetMapping("/products/minCoast")
    public List<Product> getProductsMoreThenMin(@RequestParam Integer min) {
        return productService.getProductsMoreThenMin(min);
    }
    @GetMapping("/products/maxCoast")
    public List<Product> getProductsLessThenMax(@RequestParam Integer max) {
        return productService.getProductsLessThenMax(max);
    }

}

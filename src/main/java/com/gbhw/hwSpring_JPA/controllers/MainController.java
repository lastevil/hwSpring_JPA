package com.gbhw.hwSpring_JPA.controllers;

import com.gbhw.hwSpring_JPA.models.Product;
import com.gbhw.hwSpring_JPA.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/products")
public class MainController {
    @Autowired
    ProductService productService;

    @GetMapping("/{id}")
    public @ResponseBody Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping()
    public Page<Product> getAllProducts(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                        @RequestParam(name = "min", required = false) Integer min,
                                        @RequestParam(name = "max", required = false) Integer max) {
        if (page < 1) {
            page = 1;
        }
        return productService.getAllProducts(page, min, max);
    }

    @PostMapping()
    public void addProduct(@RequestParam String title, @RequestParam Integer coast) {
        productService.addProduct(title, coast);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}

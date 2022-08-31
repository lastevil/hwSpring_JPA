package com.hw.market.controllers;


import com.hw.market.services.ProductService;
import com.hw.spring.global.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public @ResponseBody ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping()
    public Page<ProductDto> getAllProducts(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                           @RequestParam(name = "min", required = false) Integer min,
                                           @RequestParam(name = "max", required = false) Integer max) {
        if (page < 1) {
            page = 1;
        }
        return productService.getAllProducts(page, min, max);
    }

    @PostMapping()
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }

    @PutMapping()
    public void updateProduct(@RequestParam ProductDto productDto){
        productService.updateProduct(productDto);

    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}


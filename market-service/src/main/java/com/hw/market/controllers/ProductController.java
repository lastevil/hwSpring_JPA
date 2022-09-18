package com.hw.market.controllers;


import com.hw.constans.dto.ProductDto;
import com.hw.market.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Каталог",description = "Контроллер работы с товарами")
@Slf4j
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    @Operation(summary = "метод получения продукта по id")
    public @ResponseBody ProductDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }
    @Operation(summary = "метод получения списка продуктов в формате Page")
    @GetMapping()
    public Page<ProductDto> getAllProducts(@RequestParam(name = "page", defaultValue = "1") Integer page,
                                           @RequestParam(name = "min", required = false) Integer min,
                                           @RequestParam(name = "max", required = false) Integer max) {
        if (page < 1) {
            page = 1;
        }
        return productService.getAllProducts(page, min, max);
    }
    @Operation(summary = "метод добавления нового продукта")
    @PostMapping()
    public void addProduct(@RequestBody ProductDto productDto) {
        productService.addProduct(productDto);
    }
    @Operation(summary = "метод изменения продукта")
    @PutMapping()
    public void updateProduct(@RequestParam ProductDto productDto){
        productService.updateProduct(productDto);

    }
    @Operation(summary = "метод удаления продукта")
    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}


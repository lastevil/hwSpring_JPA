package com.gbhw.hwSpring_JPA.controllers;

import com.gbhw.hwSpring_JPA.dto.OrderDto;
import com.gbhw.hwSpring_JPA.dto.ProductDto;
import com.gbhw.hwSpring_JPA.services.OrderService;
import com.gbhw.hwSpring_JPA.services.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Slf4j
public class MainController {

    private Long tempUserId = 1l;//Временно для пользователя с id = 1
    private final ProductService productService;
    private final OrderService orderService;

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

    //------------------------------CART------------------------------//

    @GetMapping("/cart")
    public @ResponseBody List<OrderDto> getProductListInCartById() {
        return orderService.getCartList(tempUserId);
    }
    @PostMapping("/cart/{productId}")
    public void toCart(@PathVariable Long productId) {
        orderService.toCart(tempUserId,productId);
    }
    @DeleteMapping("/cart/{productId}")
    public void delFromCart(@PathVariable Long productId) {
        orderService.deleteProductFromCart(tempUserId,productId);
    }
    @DeleteMapping("/cartdelall/{productId}")
    public void delAllFromCart(Long productId) {
        orderService.deleteProductListFromCart(tempUserId,productId);
    }
}


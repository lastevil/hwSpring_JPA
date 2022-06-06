package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.converters.CartConverter;
import com.gbhw.hwSpring_JPA.dto.CartDto;
import com.gbhw.hwSpring_JPA.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartConverter converter;
    private final ProductService productService;
    private final Cart cart;
    public List<CartDto> getCartList() {
        if (cart.getProducts().size()==0) {
            return new ArrayList<>();
        } else
            return converter.productDtoListToCartDtoList(cart.getProducts());
    }

    public void toCart(Long productId) {
        cart.addProduct(productService.getProductById(productId));
    }

    public void deleteProductFromCart(Long productId) {
        cart.removeProduct(productService.getProductById(productId));
    }

    public void deleteProductListFromCart(Long productId) {
        cart.removeAllProductsById(productService.getProductById(productId));
    }

    public Integer getCartSum() {
        return cart.getSum();
    }

    public Integer getCartCount(){
        return cart.getProducts().size();
    }
}

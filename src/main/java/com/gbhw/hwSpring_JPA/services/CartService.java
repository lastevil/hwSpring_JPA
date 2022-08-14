package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.dto.ProductDto;
import com.gbhw.hwSpring_JPA.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productsService;
    private final CacheManager cacheManager;
    private Cart cart;

    public Cart getCurrentCart(String cartName) {
        cart = cacheManager.getCache("Cart").get(cartName, Cart.class);
        if (!Optional.ofNullable(cart).isPresent()) {
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache("Cart").put(cartName, cart);
        }
        return cart;
    }

    public void clearCart(String cartName) {
        cart = getCurrentCart(cartName);
        cart.clear();
        cacheManager.getCache("Cart").put(cartName, cart);
    }

    public void addProductByIdToCart(Long id, String cartName) {
        cart = getCurrentCart(cartName);
        ProductDto product = productsService.getProductById(id);
        cart.addProduct(product);
        cacheManager.getCache("Cart").put(cartName,cart);
    }

    public void removeOneProductByIdFromCart(Long id, String cartName){
        cart = getCurrentCart(cartName);
        ProductDto product = productsService.getProductById(id);
        cart.removeOneProduct(product);
        cacheManager.getCache("Cart").put(cartName,cart);
    }

    public void removeAllProductByIdFromCart(Long id, String cartName){
        cart = getCurrentCart(cartName);
        ProductDto product = productsService.getProductById(id);
        cart.removeAllProducts(product);
        cacheManager.getCache("Cart").put(cartName,cart);
    }
}

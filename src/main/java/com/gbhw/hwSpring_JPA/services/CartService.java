package com.gbhw.hwSpring_JPA.services;

import com.gbhw.hwSpring_JPA.dto.ProductDto;
import com.gbhw.hwSpring_JPA.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final ProductService productsService;
    private final CacheManager cacheManager;
    @Value("${spring.cache.user.name}")
    private String CACHE_CART;
    private Cart cart;

    @Cacheable(value = "Cart", key = "#cartName")
    public Cart getCurrentCart(String cartName) {
        cart = cacheManager.getCache(CACHE_CART).get(cartName, Cart.class);
        if (!Optional.ofNullable(cart).isPresent()) {
            cart = new Cart(cartName, cacheManager);
            cacheManager.getCache(CACHE_CART).put(cartName, cart);
        }
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart clearCart(String cartName){
        Cart cart = getCurrentCart(cartName);
        cart.clear();
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart addProductByIdToCart(Long id, String cartName) {
        Cart cart = getCurrentCart(cartName);
        ProductDto product = productsService.getProductById(id);
        cart.addProduct(product);
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart removeOneProductByIdFromCart(Long id, String cartName){
        Cart cart = getCurrentCart(cartName);
        ProductDto product = productsService.getProductById(id);
        cart.removeOneProduct(product);
        return cart;
    }
    @CachePut(value = "Cart", key = "#cartName")
    public Cart removeAllProductByIdFromCart(Long id, String cartName){
        Cart cart = getCurrentCart(cartName);
        ProductDto product = productsService.getProductById(id);
        cart.removeAllProducts(product);
        return cart;
    }
}

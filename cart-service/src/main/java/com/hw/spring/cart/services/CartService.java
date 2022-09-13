package com.hw.spring.cart.services;

import com.hw.constans.dto.CartDto;
import com.hw.constans.dto.ProductDto;
import com.hw.spring.cart.converters.CartConverter;
import com.hw.spring.cart.dto.OrderDetailsDto;
import com.hw.spring.cart.models.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    @Qualifier("test")
    private final CacheManager cacheManager;
    private final RestTemplate restTemplate;

    private final CartConverter cartConverter;

    @Qualifier(value = "KafkaTest")
    private final KafkaTemplate<Long, CartDto> template;
    private static final String PRODUCT_SERVICE_URL = "lb://market-service/market/api/v1/products";
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
        ProductDto product = restTemplate.getForObject(PRODUCT_SERVICE_URL+"/"+id,ProductDto.class);
        cart.addProduct(product);
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart removeOneProductByIdFromCart(Long id, String cartName){
        Cart cart = getCurrentCart(cartName);
        ProductDto product = restTemplate.getForObject(PRODUCT_SERVICE_URL+"/"+id,ProductDto.class);
        cart.removeOneProduct(product);
        return cart;
    }
    @CachePut(value = "Cart", key = "#cartName")
    public Cart removeAllProductByIdFromCart(Long id, String cartName){
        Cart cart = getCurrentCart(cartName);
        ProductDto product = restTemplate.getForObject(PRODUCT_SERVICE_URL+"/"+id,ProductDto.class);
        cart.removeAllProducts(product);
        return cart;
    }

    @CachePut(value = "Cart", key = "#cartName")
    public Cart createOrder(String cartName, OrderDetailsDto orderDetailsDto, String username) {
        Cart cart = getCurrentCart(cartName);
        CartDto sendCart = cartConverter.fromCart(cart);
        sendCart.setUsername(username);
        sendCart.setAddressId(orderDetailsDto.getAddressId());
        sendCart.setPhone(orderDetailsDto.getPhone());
        template.send("Cart",sendCart);
        cart.clear();
        return cart;
    }
}
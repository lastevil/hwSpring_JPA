package com.hw.spring.cart.models;

import com.hw.spring.global.dto.OrderItemDto;
import com.hw.spring.global.dto.ProductDto;
import lombok.Data;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Data
public class Cart {

    private List<OrderItemDto> products;

    private Integer totalProductsCount;
    private Integer totalPrice;
    private static final int DELTA_UP = 1;
    private static final int DELTA_DOWN = -1;

    public Cart() {
        this.products = new ArrayList<>();
    }

    public Cart(String cartName, CacheManager cacheManager) {
        Cart cart = cacheManager.getCache("Cart").get(cartName, Cart.class);
        if (Optional.ofNullable(cart).isPresent()) {
            this.products = cart.getProducts();
            this.totalPrice = cart.getTotalPrice();
            totalProductsCount = 0;
            for (OrderItemDto o:products){
                totalProductsCount+=o.getQuantity();
            }
        } else {
            this.products = new ArrayList<>();
            this.totalPrice = 0;
            this.totalProductsCount = 0;
            cacheManager.getCache("Cart").put(cartName, Cart.class);
        }
    }

    public boolean addProductCount(Long id) {
        for (OrderItemDto orderItemDto : products) {
            if (orderItemDto.getProductId().equals(id)) {
                orderItemDto.changeQuantity(DELTA_UP);
                recalculate();
                return true;
            }
        }
        return false;
    }

    public void addProduct(ProductDto product) {
        if (addProductCount(product.getId())) {
            return;
        }
        products.add(new OrderItemDto(product));
        recalculate();
    }

    public void removeAllProducts(ProductDto product) {
        products.removeIf(p -> p.getProductId().equals(product.getId()));
        recalculate();
    }

    public void removeOneProduct(ProductDto product) {
        for (OrderItemDto oi : products) {
            if (oi.getProductId().equals(product.getId())) {
                if (oi.getQuantity() == 1) {
                    products.remove(oi);
                } else {
                    oi.changeQuantity(DELTA_DOWN);
                }
            }
        }
        recalculate();
    }

    public void clear() {
        products.clear();
        totalPrice = 0;
        totalProductsCount = 0;
    }

    private void recalculate() {
        totalPrice = 0;
        totalProductsCount = 0;
        for (OrderItemDto o : products) {
            totalPrice += o.getPrice();
        }
        for (OrderItemDto o:products){
            totalProductsCount+=o.getQuantity();
        }
    }
}

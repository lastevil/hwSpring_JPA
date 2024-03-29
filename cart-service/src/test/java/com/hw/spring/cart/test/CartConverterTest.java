package com.hw.spring.cart.test;

import com.hw.spring.cart.api.MarketApi;
import com.hw.spring.cart.converters.CartConverter;
import com.hw.spring.cart.dto.CartDto;
import com.hw.spring.cart.dto.ProductDto;
import com.hw.spring.cart.models.Cart;
import com.hw.spring.cart.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
public class CartConverterTest {
    @Autowired
    private CartService cartService;
    @Autowired
    private CartConverter cartConverter;

    @MockBean
    private MarketApi marketApi;
    private static final String CARTNAME ="Test";
    private static final String PRODUCT_SERVICE_URL = "http://localhost:8189/market/api/v1/products";

    @Test
    public void converterTest(){
        ProductDto productDto1 = new ProductDto(1l,"test_product",20);
        ProductDto productDto2 = new ProductDto(2l,"test_product",30);
        Mockito.doReturn(productDto1).when(marketApi).getProductById(1l);
        Mockito.doReturn(productDto2).when(marketApi).getProductById(2l);

        cartService.addProductByIdToCart(1l,CARTNAME);
        cartService.addProductByIdToCart(1l,CARTNAME);
        cartService.addProductByIdToCart(2l,CARTNAME);

        Cart cart = cartService.getCurrentCart(CARTNAME);

        CartDto cartDto =cartConverter.fromCart(cart);

        Assertions.assertEquals(cart.getTotalPrice(),cartDto.getTotalPrice());
        Assertions.assertEquals(cart.getProducts().size(),cartDto.getProducts().size());
        Assertions.assertEquals(cart.getTotalProductsCount(),cartDto.getTotalProductsCount());
    }

}

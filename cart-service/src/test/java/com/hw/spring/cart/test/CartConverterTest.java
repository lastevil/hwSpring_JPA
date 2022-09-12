package com.hw.spring.cart.test;

import com.hw.constans.dto.CartDto;
import com.hw.constans.dto.ProductDto;
import com.hw.spring.cart.converters.CartConverter;
import com.hw.spring.cart.dto.OrderDetailsDto;
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
    private RestTemplate restTemplate;
    private static final String CARTNAME ="Test";
    private static final String PRODUCT_SERVICE_URL = "http://localhost:8189/market/api/v1/products";

    @Test
    public void converterTest(){
        ProductDto productDto1 = new ProductDto(1l,"test_product",20);
        ProductDto productDto2 = new ProductDto(2l,"test_product",30);
        Mockito.doReturn(productDto1).when(restTemplate).getForObject(PRODUCT_SERVICE_URL+"/"+1,ProductDto.class);
        Mockito.doReturn(productDto2).when(restTemplate).getForObject(PRODUCT_SERVICE_URL+"/"+2,ProductDto.class);

        cartService.addProductByIdToCart(1l,CARTNAME);
        cartService.addProductByIdToCart(1l,CARTNAME);
        cartService.addProductByIdToCart(2l,CARTNAME);

        Cart cart = cartService.getCurrentCart(CARTNAME);

        CartDto cartDto =cartConverter.fromCart(cart,new OrderDetailsDto(),null);

        Assertions.assertEquals(cart.getTotalPrice(),cartDto.getTotalPrice());
        Assertions.assertEquals(cart.getProducts().size(),cartDto.getProducts().size());
        Assertions.assertEquals(cart.getTotalProductsCount(),cartDto.getTotalProductsCount());
    }

}

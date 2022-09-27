package com.hw.spring.cart.test;


import com.hw.spring.cart.api.MarketApi;
import com.hw.spring.cart.dto.ProductDto;
import com.hw.spring.cart.models.Cart;
import com.hw.spring.cart.services.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class CartServiceTest {
    @Autowired
    private CartService cartService;

    @MockBean
    private MarketApi marketApi;
    private static final String CARTNAME ="Test";
    private static final String PRODUCT_SERVICE_URL = "http://localhost:8189/market/api/v1/products";

    @BeforeEach
    public void clearCart(){cartService.clearCart(CARTNAME);}

    @Test
    public void createCartTest(){
       Cart testCart = cartService.getCurrentCart(CARTNAME);
        Assertions.assertNotNull(testCart);
    }

    @Test
    public void addProductTest(){
        ProductDto productDto = new ProductDto(1l,"test_product",20);
        Mockito.doReturn(productDto).when(marketApi).getProductById(1l);

        cartService.addProductByIdToCart(1l,CARTNAME);

        Cart testCart = cartService.getCurrentCart(CARTNAME);

        Assertions.assertEquals(testCart.getProducts().size(),1);
        Assertions.assertEquals(testCart.getTotalPrice(),20);

        cartService.addProductByIdToCart(1l,CARTNAME);

        testCart = cartService.getCurrentCart(CARTNAME);

        Assertions.assertEquals(testCart.getTotalProductsCount(),2);
        Assertions.assertEquals(testCart.getTotalPrice(),40);
        Assertions.assertEquals(testCart.getProducts().size(),1);
    }

    @Test
    public void deleteProductTest(){
        ProductDto productDto1 = new ProductDto(1l,"test_product",20);
        ProductDto productDto2 = new ProductDto(2l,"test_product",30);
        Mockito.doReturn(productDto1).when(marketApi).getProductById(1l);
        Mockito.doReturn(productDto2).when(marketApi).getProductById(2l);

        cartService.addProductByIdToCart(1l,CARTNAME);
        cartService.addProductByIdToCart(2l,CARTNAME);

        cartService.removeOneProductByIdFromCart(1l,CARTNAME);

        Cart testCart = cartService.getCurrentCart(CARTNAME);

        Assertions.assertEquals(testCart.getTotalPrice(),30);
        Assertions.assertEquals(testCart.getTotalProductsCount(),1);
        Assertions.assertEquals(testCart.getProducts().get(0).getProductId(),productDto2.getId());
    }

    @Test
    public void deleteAllProductTest(){
        ProductDto productDto1 = new ProductDto(1l,"test_product",20);
        ProductDto productDto2 = new ProductDto(2l,"test_product",30);
        Mockito.doReturn(productDto1).when(marketApi).getProductById(1l);
        Mockito.doReturn(productDto2).when(marketApi).getProductById(2l);

        cartService.addProductByIdToCart(1l,CARTNAME);
        cartService.addProductByIdToCart(1l,CARTNAME);
        cartService.addProductByIdToCart(2l,CARTNAME);

        cartService.removeAllProductByIdFromCart(1l,CARTNAME);

        Cart testCart = cartService.getCurrentCart(CARTNAME);

        Assertions.assertEquals(testCart.getTotalPrice(),30);
        Assertions.assertEquals(testCart.getTotalProductsCount(),1);
        Assertions.assertEquals(testCart.getProducts().get(0).getProductId(),productDto2.getId());
    }
}

package com.hw.spring.cart.test;

import com.hw.spring.cart.dto.ProductDto;
import com.hw.spring.cart.models.Cart;
import com.hw.spring.cart.services.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CartControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CartService service;

    @Test
    public void getCartTest() throws Exception {
        Cart cart = new Cart();
        ProductDto productDto = new ProductDto(1l,"Test",20);
        cart.addProduct(productDto);
        given(service.getCurrentCart("test")).willReturn(cart);

        mvc.perform(post("/api/v1/cart").content("test")
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.products").isArray())
                .andExpect(jsonPath("$.products",hasSize(1)))
                .andExpect(jsonPath("$.products[0].title",is(productDto.getTitle())))
                .andExpect(jsonPath("$.totalPrice").isNumber());
    }


    @Test
    public void getProductCountTest() throws Exception {
        Cart cart = new Cart();
        ProductDto productDto = new ProductDto(1l,"Test",20);
        cart.addProduct(productDto);
        given(service.getCurrentCart("test")).willReturn(cart);

        mvc.perform(post("/api/v1/cart/productCount").content("test")
                        .contentType(MediaType.APPLICATION_JSON)
                ).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$").isNumber());
    }
}

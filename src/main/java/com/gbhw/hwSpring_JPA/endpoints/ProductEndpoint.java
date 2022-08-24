package com.gbhw.hwSpring_JPA.endpoints;

import com.gbhw.hwSpring_JPA.dto.ProductDto;
import com.gbhw.hwSpring_JPA.services.ProductService;
import com.gbhw.hwSpring_JPA.soap.soap.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.math.BigInteger;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAME_SPACE = "http://www.mvg.com/spring/ws/products";
    private final ProductService productService;

    @PayloadRoot(namespace = NAME_SPACE, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productService.getAllProductList().forEach((p -> {
            Product product = new Product();
            product.setId(p.getId());
            product.setTitle(p.getTitle());
            product.setPrice(BigInteger.valueOf(p.getPrice()));
            response.getProducts().add(product);
        }));
        return response;
    }

    @PayloadRoot(namespace = NAME_SPACE, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        ProductDto p =productService.getProductById(request.getId());
        Product product = new Product();
        product.setId(p.getId());
        product.setTitle(p.getTitle());
        product.setPrice(BigInteger.valueOf(p.getPrice()));
        response.setProduct(product);
        return response;
    }
}

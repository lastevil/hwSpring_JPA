package com.hw.spring.global.dto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    private Long productId;
    private String title;
    private Integer quantity;
    private Integer pricePerProduct;
    private Integer price;

    public OrderItemDto(ProductDto product){
        this.productId = product.getId();
        this.title = product.getTitle();
        this.quantity = 1;
        this.pricePerProduct = product.getPrice();
        this.price = product.getPrice();
    }

    public void changeQuantity(int delta){
        this.quantity += delta;
        this.price = this.quantity * this.pricePerProduct;
    }
}

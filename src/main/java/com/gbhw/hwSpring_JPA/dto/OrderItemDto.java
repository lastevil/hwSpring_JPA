package com.gbhw.hwSpring_JPA.dto;
import lombok.Data;

@Data
public class OrderItemDto {
    private Long productId;
    private String title;
    private Integer quantity;
    private Integer coastPerProduct;
    private Integer coast;

    public OrderItemDto(ProductDto product){
        this.productId = product.getId();
        this.title = product.getTitle();
        this.quantity = 1;
        this.coastPerProduct = product.getCoast();
        this.coast = product.getCoast();
    }

    public void changeQuantity(int delta){
        this.quantity += delta;
        this.coast = this.quantity * this.coastPerProduct;
    }
}

package com.hw.spring.cart.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    @Schema(description = "id продукта", example = "2")
    private Long productId;
    @Schema(description = "Наименование продукта",example = "Печенье")
    private String title;
    @Schema(description = "Колличество продуктов",example = "2")
    private Integer quantity;
    @Schema(description = "Стоимость одного продукта",example = "50")
    private Integer pricePerProduct;
    @Schema(description = "Стоимость всех подуктов",example = "100")
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

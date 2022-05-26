package com.gbhw.hwSpring_JPA.models;

import com.gbhw.hwSpring_JPA.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "fix_coast")
    private Integer fix_coast;
    @Column(name = "product_id")
    private Long product_id;
    @Column(name = "status")
    private String status;
    @OneToMany(mappedBy = "order")
    List<Product> productList = new ArrayList<>();

    public Order(Long user_id, String status) {
        this.user_id = user_id;
        this.status = status;
    }

    public void addProduct(Product product){
        productList.add(product);
        this.product_id=product.getId();
        product.setOrder(this);
    }

    public void removeProduct(Product product){
        productList.remove(product);
        product.setOrder(null);
    }
}

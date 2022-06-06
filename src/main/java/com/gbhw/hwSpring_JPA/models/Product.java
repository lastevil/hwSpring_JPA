package com.gbhw.hwSpring_JPA.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "coast")
    private Integer coast;

    @ManyToOne
    @JoinTable(name = "orders")
    @JoinColumn (name = "product_id")
    private Order order;

    public Product(Long id, String title, Integer coast) {
        this.id= id;
        this.title=title;
        this.coast=coast;
    }
}

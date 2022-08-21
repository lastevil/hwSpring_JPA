package com.gbhw.hwSpring_JPA.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @Column(name = "price")
    private Integer price;

    @ManyToOne
    @JoinTable(name = "orders")
    @JoinColumn (name = "product_id")
    private Order order;

    public Product(Long id, String title, Integer price) {
        this.id= id;
        this.title=title;
        this.price = price;
    }
}

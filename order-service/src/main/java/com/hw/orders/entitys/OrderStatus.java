package com.hw.orders.entitys;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Data
@NoArgsConstructor
@Table(name = "order_statuses")
public class OrderStatus {
    @Id
    private Long id;
    @Column(name = "order_status")
    private String orderStatus;
}

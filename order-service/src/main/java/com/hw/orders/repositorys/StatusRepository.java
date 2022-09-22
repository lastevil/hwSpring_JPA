package com.hw.orders.repositorys;

import com.hw.orders.entitys.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<OrderStatus, Long> {
}

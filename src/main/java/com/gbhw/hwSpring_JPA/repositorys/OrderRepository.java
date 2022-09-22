package com.gbhw.hwSpring_JPA.repositorys;

import com.gbhw.hwSpring_JPA.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}

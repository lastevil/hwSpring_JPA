package com.gbhw.hwSpring_JPA.repositorys;

import com.gbhw.hwSpring_JPA.entitys.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Query("select o.product_id from Order o where o.user_id=:user_id and o.status like :status")
    List<Long> getByUserIdInCart(Long user_id, String status);
}

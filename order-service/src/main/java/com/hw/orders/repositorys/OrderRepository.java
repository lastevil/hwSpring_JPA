package com.hw.orders.repositorys;

import com.hw.orders.entitys.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    List<Order> findAllByUsername (@Param("userName")String userName);
    @Query("select o.orderStatus.id from Order o where o.id=:orderId")
    Integer findStatusIdById(@Param("orderId")Long orderId);
}

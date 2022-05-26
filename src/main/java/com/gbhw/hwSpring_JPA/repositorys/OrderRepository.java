package com.gbhw.hwSpring_JPA.repositorys;

import com.gbhw.hwSpring_JPA.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {
    @Query("select o.id from Order o where o.user_id=:user_id and o.status like :status")
    List<Long> getByUserIdInCart(Long user_id, String status);
    @Query("select o.productList from Order o where :product_id in(o.productList) and o.user_id=:user_id and o.status like :status")
    void getProductInCart(Long user_id, Long product_id, String status);
    @Query("select o.productList from Order o where o.user_id=:user_id and o.status like :status")
    void getAllProductInCart(Long user_id, String status);
}

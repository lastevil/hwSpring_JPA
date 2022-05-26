package com.gbhw.hwSpring_JPA.repositorys;

import com.gbhw.hwSpring_JPA.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    @Query("select p from Product p where p.id=:id")
    Optional<Product> findById(Long id);
    boolean existsProductById(Long id);
}

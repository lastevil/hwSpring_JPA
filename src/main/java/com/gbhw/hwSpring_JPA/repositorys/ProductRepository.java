package com.gbhw.hwSpring_JPA.repositorys;

import com.gbhw.hwSpring_JPA.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.id=:id")
    Optional<Product> findById(Long id);

    @Query("select p from Product p where p.coast>:min and p.coast<:max")
    List<Product> findAllBetweenCoast(Integer min, Integer max);

    @Query("select p from Product p where p.coast>:min")
    List<Product> getProductsMoreThenMin(Integer min);

    @Query("select p from Product p where p.coast<:max")
    List<Product> getProductsLessThenMax(Integer max);
}

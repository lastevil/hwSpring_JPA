package com.gbhw.hwSpring_JPA.repositorys;

import com.gbhw.hwSpring_JPA.entitys.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
   @Query("SELECT a from Address a where a.user.id=:id")
   List<Address> findAddressesByUserId(@Param("id")long id);
}

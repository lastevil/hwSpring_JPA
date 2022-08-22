package com.gbhw.hwSpring_JPA.repositorys;


import com.gbhw.hwSpring_JPA.entitys.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Collection<Role> findAllById(@Param("id") Long id);
}

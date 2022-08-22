package com.gbhw.hwSpring_JPA.repositorys;

import com.gbhw.hwSpring_JPA.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.email=:email")
    Optional<User> findByEmail(@Param("email") String email);
}

package dev.joint.library_management.repository.security;

import dev.joint.library_management.entity.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u left join fetch u.authorities where u.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

}

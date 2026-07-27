package dev.joint.library_management.repository.security;

import dev.joint.library_management.entity.security.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority,Integer> {


}

package dev.joint.library_management.repository;

import dev.joint.library_management.entity.LoanItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanItemRepository extends JpaRepository<LoanItem, Integer> {
}

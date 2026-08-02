package dev.joint.library_management.repository;

import dev.joint.library_management.entity.Loan;
import dev.joint.library_management.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Integer> {
    List<Loan> findByMemberId(Integer memberId);

    List<Loan> findByStatus(LoanStatus status);


    @Query("select l from Loan l where l.status = dev.joint.library_management.enums.LoanStatus.ACTIVE " +
            "and l.dueDate < :today")
    List<Loan> findOverdueLoans(@Param("today") LocalDate today);



    @Query(value = "select m.id as memberId, m.name as memberName, count(l.id) as activeLoans " +
            "from members m " +
            "join loans l on l.member_id = m.id and l.status = 'ACTIVE' " +
            "group by m.id, m.name " +
            "order by activeLoans desc", nativeQuery = true)
    List<Object[]> countActiveLoansPerMemberNative();
}

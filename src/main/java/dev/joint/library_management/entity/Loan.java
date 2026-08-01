package dev.joint.library_management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.joint.library_management.enums.LoanStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "loans")
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore
    private Member member;

    private LocalDate loanDate;

    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status;


    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoanItem> items = new ArrayList<>();

    public void addItem(LoanItem item) {
        items.add(item);
        item.setLoan(this);
    }
}

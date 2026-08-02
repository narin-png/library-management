package dev.joint.library_management.dto;

import dev.joint.library_management.enums.LoanStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanResponseDto {
    private Integer id;
    private String memberName;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LoanStatus status;
    private List<LoanItemResponseDto> items;
}

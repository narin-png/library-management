package dev.joint.library_management.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class LoanRequestDto {
    @NotNull(message = "Member id is required")
    private Integer memberId;

    @NotEmpty(message = "At least one book id is required")
    private List<Integer> bookIds;

    private LocalDate dueDate;
}

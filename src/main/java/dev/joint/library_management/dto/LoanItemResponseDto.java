package dev.joint.library_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanItemResponseDto {
    private Integer id;
    private String bookTitle;
    private boolean returned;
    private LocalDate returnDate;
}

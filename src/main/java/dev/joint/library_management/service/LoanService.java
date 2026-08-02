package dev.joint.library_management.service;

import dev.joint.library_management.dto.LoanRequestDto;
import dev.joint.library_management.dto.LoanResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LoanService {
    LoanResponseDto createLoan(LoanRequestDto request);
    LoanResponseDto returnLoanItem(Integer loanItemId);
    LoanResponseDto getLoanById(Integer id);
    Page<LoanResponseDto> getAllLoans(Pageable pageable);
}

package dev.joint.library_management.controller;

import dev.joint.library_management.dto.LoanRequestDto;
import dev.joint.library_management.dto.LoanResponseDto;
import dev.joint.library_management.service.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/loans")
@Tag(name = "Loan", description = "Book borrowing/return APIs")
public class LoanController {
    private final LoanService loanService;

    @PostMapping
    @Operation(summary = "Borrow one or more books (creates a Loan with LoanItems)")
    public ResponseEntity<LoanResponseDto> createLoan(@Valid @RequestBody LoanRequestDto request) {
        return new ResponseEntity<>(loanService.createLoan(request), HttpStatus.CREATED);
    }

    @PostMapping("/items/{loanItemId}/return")
    @Operation(summary = "Return a single borrowed book")
    public ResponseEntity<LoanResponseDto> returnLoanItem(@PathVariable Integer loanItemId) {
        return ResponseEntity.ok(loanService.returnLoanItem(loanItemId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get loan by id")
    public ResponseEntity<LoanResponseDto> getLoanById(@PathVariable Integer id) {
        return ResponseEntity.ok(loanService.getLoanById(id));
    }

    @GetMapping
    @Operation(summary = "Get all loans")
    public ResponseEntity<Page<LoanResponseDto>> getAllLoans(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(loanService.getAllLoans(pageable));
    }
}


package dev.joint.library_management.service.impl;

import dev.joint.library_management.dto.LoanItemResponseDto;
import dev.joint.library_management.dto.LoanRequestDto;
import dev.joint.library_management.dto.LoanResponseDto;
import dev.joint.library_management.entity.Book;
import dev.joint.library_management.entity.Loan;
import dev.joint.library_management.entity.LoanItem;
import dev.joint.library_management.entity.Member;
import dev.joint.library_management.enums.LoanStatus;
import dev.joint.library_management.exception.BookNotAvailableException;
import dev.joint.library_management.exception.ResourceNotFoundException;
import dev.joint.library_management.repository.BookRepository;
import dev.joint.library_management.repository.LoanItemRepository;
import dev.joint.library_management.repository.LoanRepository;
import dev.joint.library_management.repository.MemberRepository;
import dev.joint.library_management.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LoanServiceImpl implements LoanService {
    private final LoanRepository loanRepository;
    private final LoanItemRepository loanItemRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Override
    public LoanResponseDto createLoan(LoanRequestDto request) {

        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        Loan loan = new Loan();
        loan.setMember(member);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(request.getDueDate() != null ? request.getDueDate() : LocalDate.now().plusDays(14));
        loan.setStatus(LoanStatus.ACTIVE);
        for (Integer bookId : request.getBookIds()) {
            Book book = bookRepository.findById(bookId)
                    .orElseThrow(() -> new ResourceNotFoundException("Book not found: id=" + bookId));

            if (book.getAvailableCopies() == null || book.getAvailableCopies() <= 0) {
                throw new BookNotAvailableException(
                        "Book '" + book.getTitle() + "' has no available copies");
            }

            book.setAvailableCopies(book.getAvailableCopies() - 1);

            LoanItem item = new LoanItem();
            item.setBook(book);
            loan.addItem(item);
        }

        Loan saved = loanRepository.save(loan);

        return toResponseDto(saved);
    }
    @Override
    public LoanResponseDto returnLoanItem(Integer loanItemId) {

        LoanItem item = loanItemRepository.findById(loanItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Loan item not found"));

        if (item.isReturned()) {
            throw new IllegalStateException("This item was already returned");
        }

        item.setReturned(true);
        item.setReturnDate(LocalDate.now());

        Book book = item.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        Loan loan = item.getLoan();
        boolean allReturned = loan.getItems().stream().allMatch(LoanItem::isReturned);
        loan.setStatus(allReturned ? LoanStatus.RETURNED : LoanStatus.PARTIALLY_RETURNED);

        loanItemRepository.save(item);

        return toResponseDto(loan);
    }

    @Override
    public LoanResponseDto getLoanById(Integer id) {
        Loan loan = loanRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found"));

        return toResponseDto(loan);
    }

    @Override
    public Page<LoanResponseDto> getAllLoans(Pageable pageable) {

        Page<Loan> loanPage = loanRepository.findAll(pageable);

        List<Integer> ids = loanPage.getContent().stream()
                .map(Loan::getId)
                .collect(Collectors.toList());
        List<Loan> loansWithDetails = loanRepository.findAllWithDetailsByIdIn(ids);


        Map<Integer, Loan> loanById = loansWithDetails.stream()
                .collect(Collectors.toMap(Loan::getId, loan -> loan));

        List<LoanResponseDto> dtos = ids.stream()
                .map(id -> toResponseDto(loanById.get(id)))
                .collect(Collectors.toList());

        return new PageImpl<>(dtos, pageable, loanPage.getTotalElements());
    }

    private LoanResponseDto toResponseDto(Loan loan) {
        LoanResponseDto dto = new LoanResponseDto();
        dto.setId(loan.getId());
        dto.setMemberName(loan.getMember().getName());
        dto.setLoanDate(loan.getLoanDate());
        dto.setDueDate(loan.getDueDate());
        dto.setStatus(loan.getStatus());

        List<LoanItemResponseDto> items = loan.getItems().stream()
                .map(item -> new LoanItemResponseDto(
                        item.getId(),
                        item.getBook().getTitle(),
                        item.isReturned(),
                        item.getReturnDate()))
                .collect(Collectors.toList());

        dto.setItems(items);

        return dto;
    }
}


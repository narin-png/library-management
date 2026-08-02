package dev.joint.library_management.service;

import dev.joint.library_management.dto.LoanRequestDto;
import dev.joint.library_management.entity.Author;
import dev.joint.library_management.entity.Book;
import dev.joint.library_management.entity.Member;
import dev.joint.library_management.exception.BookNotAvailableException;
import dev.joint.library_management.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class LoanServiceTransactionTest {
    @Autowired
    private LoanService loanService;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanItemRepository loanItemRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Book availableBook;
    private Book unavailableBook;

    @BeforeEach
    void setUp() {
        // Clean slate - respect FK order (children before parents)
        loanItemRepository.deleteAll();
        loanRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        memberRepository.deleteAll();

        Author author = new Author();
        author.setName("Test Author");
        author.setEmail("author@test.com");
        author = authorRepository.save(author);

        member = new Member();
        member.setName("Test Member");
        member.setEmail("member@test.com");
        member.setPhone("0000000");
        member = memberRepository.save(member);

        availableBook = new Book();
        availableBook.setTitle("Available Book");
        availableBook.setPublishedYear(2020);
        availableBook.setAuthor(author);
        availableBook.setTotalCopies(3);
        availableBook.setAvailableCopies(3);
        availableBook = bookRepository.save(availableBook);

        unavailableBook = new Book();
        unavailableBook.setTitle("Unavailable Book");
        unavailableBook.setPublishedYear(2021);
        unavailableBook.setAuthor(author);
        unavailableBook.setTotalCopies(1);
        unavailableBook.setAvailableCopies(0); // already fully borrowed
        unavailableBook = bookRepository.save(unavailableBook);
    }

    @Test
    void createLoan_succeeds_whenAllBooksAvailable() {
        LoanRequestDto request = new LoanRequestDto(member.getId(), List.of(availableBook.getId()), null);

        loanService.createLoan(request);

        Book refreshed = bookRepository.findById(availableBook.getId()).orElseThrow();
        assertEquals(2, refreshed.getAvailableCopies(), "Available copies should decrease by 1");
        assertEquals(1, loanRepository.findAll().size(), "One loan should be persisted");
        assertEquals(1, loanItemRepository.findAll().size(), "One loan item should be persisted");
    }

    @Test
    void createLoan_rollsBackEverything_whenOneBookIsUnavailable() {
        // Request both books together: the available one AND the unavailable one.
        // The unavailable one should trigger a failure partway through the
        // transaction - after the available book's copy count was already
        // decremented in memory.
        LoanRequestDto request = new LoanRequestDto(
                member.getId(),
                List.of(availableBook.getId(), unavailableBook.getId()),
                null
        );

        assertThrows(BookNotAvailableException.class, () -> loanService.createLoan(request));

        // If @Transactional rollback works correctly, NONE of this should
        // have been persisted - not even the availableBook's decrement that
        // happened before the failure was hit.
        Book refreshedAvailable = bookRepository.findById(availableBook.getId()).orElseThrow();
        assertEquals(3, refreshedAvailable.getAvailableCopies(),
                "Available copies must be unchanged - rollback should have undone the in-memory decrement");

        assertEquals(0, loanRepository.findAll().size(), "No loan should have been persisted");
        assertEquals(0, loanItemRepository.findAll().size(), "No loan item should have been persisted");
    }
}

package dev.joint.library_management.controller;

import dev.joint.library_management.dto.BookRequestDto;
import dev.joint.library_management.dto.BookResponseDto;
import dev.joint.library_management.dto.CategoryBookCountDto;
import dev.joint.library_management.service.BookService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
@Tag(name = "Book", description = "Book management APIs")
public class BookController {
    private final BookService bookService;
    @GetMapping
    @Operation(summary = "Get all books")
    public ResponseEntity<Page<BookResponseDto>> getAllBooks(
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        return ResponseEntity.ok(bookService.getAllBooks(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get book by id")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable Integer id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping
    @Operation(summary = "Create book")
    public ResponseEntity<BookResponseDto> createBook(@Valid @RequestBody BookRequestDto request) {
        return new ResponseEntity<>(bookService.createBook(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update book")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable Integer id,
                                                      @Valid @RequestBody BookRequestDto request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete book")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/category/{categoryName}")
    @Operation(summary = "Get books by category name (JPQL join query)")
    public ResponseEntity<List<BookResponseDto>> getBooksByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(bookService.getBooksByCategory(categoryName));
    }

    @GetMapping("/stats/categories")
    @Operation(summary = "Get book count per category (native SQL query)")
    public ResponseEntity<List<CategoryBookCountDto>> getCategoryBookCounts() {
        return ResponseEntity.ok(bookService.getCategoryBookCounts());
    }

    @GetMapping("/search")
    @Operation(summary = "Dynamic book search with optional filters")
    public ResponseEntity<Page<BookResponseDto>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) Integer fromYear,
            @RequestParam(required = false) Integer toYear,
            @RequestParam(required = false) Boolean available,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {

        return ResponseEntity.ok(
                bookService.searchBooks(title, authorName, categoryName, fromYear, toYear, available, pageable));
    }
}

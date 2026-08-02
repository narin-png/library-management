package dev.joint.library_management.service;

import dev.joint.library_management.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookService {
   // List<BookResponseDto> getAllBooks();
    Page<BookResponseDto> getAllBooks(Pageable pageable);
    BookResponseDto getBookById(Integer id);
    BookResponseDto createBook(BookRequestDto bookRequestDto);
    BookResponseDto updateBook(Integer id, BookRequestDto bookRequestDto);
    void deleteBook(Integer id);
    List<BookResponseDto> getBooksByCategory(String categoryName);
    List<CategoryBookCountDto> getCategoryBookCounts();
    Page<BookResponseDto> searchBooks(String title, String authorName, String categoryName,
                                   Integer fromYear, Integer toYear, Boolean available,
                                   Pageable pageable);
}

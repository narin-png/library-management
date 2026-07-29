package dev.joint.library_management.service;

import dev.joint.library_management.dto.AuthorRequestDto;
import dev.joint.library_management.dto.AuthorResponseDto;
import dev.joint.library_management.dto.BookRequestDto;
import dev.joint.library_management.dto.BookResponseDto;
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
}

package dev.joint.library_management.service.impl;

import dev.joint.library_management.dto.BookRequestDto;
import dev.joint.library_management.dto.BookResponseDto;
import dev.joint.library_management.repository.BookRepository;
import dev.joint.library_management.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    @Override
    public List<BookResponseDto> getAllBooks() {
        return List.of();
    }

    @Override
    public BookResponseDto getBookById(Integer id) {
        return null;
    }

    @Override
    public BookResponseDto createBook(BookRequestDto bookRequestDto) {
        return null;
    }

    @Override
    public BookResponseDto updateBook(Integer id, BookRequestDto bookRequestDto) {
        return null;
    }

    @Override
    public void deleteBook(Integer id) {

    }

    private final BookRepository bookRepository;
}

package dev.joint.library_management.service.impl;

import dev.joint.library_management.config.EnhancedObjectMapper;
import dev.joint.library_management.dto.BookRequestDto;
import dev.joint.library_management.dto.BookResponseDto;
import dev.joint.library_management.entity.Author;
import dev.joint.library_management.entity.Book;
import dev.joint.library_management.exception.ResourceNotFoundException;
import dev.joint.library_management.repository.AuthorRepository;
import dev.joint.library_management.repository.BookRepository;
import dev.joint.library_management.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final EnhancedObjectMapper enhancedObjectMapper;
    private final AuthorRepository authorRepository;
    @Override
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {


        return bookRepository.findAll(pageable)
                .map(book -> {

                    BookResponseDto dto =
                            enhancedObjectMapper.convertValue(book, BookResponseDto.class);

                    if (book.getAuthor() != null) {
                        dto.setAuthorName(book.getAuthor().getName());
                    }

                    return dto;
                });
    }

    @Override
    public BookResponseDto getBookById(Integer id) {


        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        BookResponseDto dto =
                enhancedObjectMapper.convertValue(book, BookResponseDto.class);

        if (book.getAuthor() != null) {
            dto.setAuthorName(book.getAuthor().getName());
        }

        return dto;
    }

    @Override
    public BookResponseDto createBook(BookRequestDto request) {

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setPublishedYear(request.getPublishedYear());
        book.setAuthor(author);

        Book saved = bookRepository.save(book);

        BookResponseDto dto =
                enhancedObjectMapper.convertValue(saved, BookResponseDto.class);

        dto.setAuthorName(saved.getAuthor().getName());

        return dto;
    }

    @Override
    public BookResponseDto updateBook(Integer id, BookRequestDto request) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        book.setTitle(request.getTitle());
        book.setPublishedYear(request.getPublishedYear());

        if (!book.getAuthor().getId().equals(request.getAuthorId())) {
            Author author = authorRepository.findById(request.getAuthorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

            book.setAuthor(author);
        }

        Book updated = bookRepository.save(book);

        BookResponseDto dto =
                enhancedObjectMapper.convertValue(updated, BookResponseDto.class);

        dto.setAuthorName(updated.getAuthor().getName());

        return dto;
    }

    @Override
    public void deleteBook(Integer id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        bookRepository.delete(book);
    }
}

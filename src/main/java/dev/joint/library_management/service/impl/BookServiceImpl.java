package dev.joint.library_management.service.impl;

import dev.joint.library_management.config.EnhancedObjectMapper;
import dev.joint.library_management.dto.BookRequestDto;
import dev.joint.library_management.dto.BookResponseDto;
import dev.joint.library_management.entity.Author;
import dev.joint.library_management.entity.Book;
import dev.joint.library_management.repository.AuthorRepository;
import dev.joint.library_management.repository.BookRepository;
import dev.joint.library_management.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final EnhancedObjectMapper enhancedObjectMapper;
    private final AuthorRepository authorRepository;
    @Override
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {

        return bookRepository.findAll(pageable)
                .map(book -> {

                    BookResponseDto dto = new BookResponseDto();

                    dto.setId(book.getId());
                    dto.setTitle(book.getTitle());
                    dto.setPublishedYear(book.getPublishedYear());

                    if (book.getAuthor() != null) {
                        dto.setAuthorName(book.getAuthor().getName());
                    }

                    return dto;
                });
    }

    @Override
    public BookResponseDto getBookById(Integer id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id: " + id));

        BookResponseDto dto = new BookResponseDto();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setPublishedYear(book.getPublishedYear());

        if (book.getAuthor() != null) {
            dto.setAuthorName(book.getAuthor().getName());
        }

        return dto;
    }

    @Override
    public BookResponseDto createBook(BookRequestDto request) {

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setPublishedYear(request.getPublishedYear());
        book.setAuthor(author);

        Book saved = bookRepository.save(book);

        BookResponseDto dto = new BookResponseDto();
        dto.setId(saved.getId());
        dto.setTitle(saved.getTitle());
        dto.setPublishedYear(saved.getPublishedYear());
        dto.setAuthorName(saved.getAuthor().getName());

        return dto;
    }

    @Override
    public BookResponseDto updateBook(Integer id, BookRequestDto request) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        book.setTitle(request.getTitle());
        book.setPublishedYear(request.getPublishedYear());
        book.setAuthor(author);

        Book updated = bookRepository.save(book);

        BookResponseDto dto = new BookResponseDto();
        dto.setId(updated.getId());
        dto.setTitle(updated.getTitle());
        dto.setPublishedYear(updated.getPublishedYear());
        dto.setAuthorName(updated.getAuthor().getName());

        return dto;
    }

    @Override
    public void deleteBook(Integer id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        bookRepository.delete(book);
    }
}

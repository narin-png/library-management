package dev.joint.library_management.service.impl;

import dev.joint.library_management.config.EnhancedObjectMapper;
import dev.joint.library_management.dto.BookRequestDto;
import dev.joint.library_management.dto.BookResponseDto;
import dev.joint.library_management.entity.Author;
import dev.joint.library_management.entity.Book;
import dev.joint.library_management.entity.Category;
import dev.joint.library_management.exception.ResourceNotFoundException;
import dev.joint.library_management.repository.AuthorRepository;
import dev.joint.library_management.repository.BookRepository;
import dev.joint.library_management.repository.CategoryRepository;
import dev.joint.library_management.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final EnhancedObjectMapper enhancedObjectMapper;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Page<BookResponseDto> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable).map(this::toResponseDto);
    }

    @Override
    public BookResponseDto getBookById(Integer id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        return toResponseDto(book);
    }

    @Override
    public BookResponseDto createBook(BookRequestDto request) {

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found"));

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setPublishedYear(request.getPublishedYear());
        book.setAuthor(author);
        book.setTotalCopies(request.getTotalCopies());
        book.setAvailableCopies(request.getTotalCopies());
        book.setCategories(resolveCategories(request.getCategoryIds()));

        Book saved = bookRepository.save(book);

        return toResponseDto(saved);
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
        int borrowed = book.getTotalCopies() - book.getAvailableCopies();
        int newTotal = request.getTotalCopies();
        book.setTotalCopies(newTotal);
        book.setAvailableCopies(Math.max(newTotal - borrowed, 0));

        book.setCategories(resolveCategories(request.getCategoryIds()));

        Book updated = bookRepository.save(book);
        return toResponseDto(updated);
    }

    @Override
    public void deleteBook(Integer id) {

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        bookRepository.delete(book);
    }
    private Set<Category> resolveCategories(Set<Integer> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return new HashSet<>();
        }

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(categoryIds));

        if (categories.size() != categoryIds.size()) {
            throw new ResourceNotFoundException("One or more categories not found");
        }

        return categories;
    }

    private BookResponseDto toResponseDto(Book book) {
        BookResponseDto dto = enhancedObjectMapper.convertValue(book, BookResponseDto.class);

        if (book.getAuthor() != null) {
            dto.setAuthorName(book.getAuthor().getName());
        }

        dto.setCategoryNames(
                book.getCategories().stream()
                        .map(Category::getName)
                        .collect(Collectors.toSet())
        );

        return dto;
    }
}

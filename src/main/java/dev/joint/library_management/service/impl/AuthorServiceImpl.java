package dev.joint.library_management.service.impl;

import dev.joint.library_management.config.EnhancedObjectMapper;
import dev.joint.library_management.dto.AuthorRequestDto;
import dev.joint.library_management.dto.AuthorResponseDto;
import dev.joint.library_management.entity.Author;
import dev.joint.library_management.repository.AuthorRepository;
import dev.joint.library_management.repository.BookRepository;
import dev.joint.library_management.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final EnhancedObjectMapper enhancedObjectMapper;
    @Override
    public Page<AuthorResponseDto> getAllAuthors(Pageable pageable) {
        return authorRepository.findAll(pageable)
                .map(author -> enhancedObjectMapper.convertValue(author, AuthorResponseDto.class));
    }

    @Override
    public AuthorResponseDto getAuthorById(Integer id) {
        return enhancedObjectMapper.convertValue(
                authorRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Author not found with id: " + id)),
                AuthorResponseDto.class);
    }

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {
        Author author = enhancedObjectMapper.convertValue(authorRequestDto, Author.class);
        Author savedAuthor = authorRepository.save(author);
        return enhancedObjectMapper.convertValue(savedAuthor, AuthorResponseDto.class);
    }

    @Override
    public AuthorResponseDto updateAuthor(Integer id, AuthorRequestDto authorRequestDto) {
        Author existingAuthor = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        existingAuthor.setName(authorRequestDto.getName());
        existingAuthor.setEmail(authorRequestDto.getEmail());

        Author updatedAuthor = authorRepository.save(existingAuthor);

        return enhancedObjectMapper.convertValue(updatedAuthor, AuthorResponseDto.class);
    }

    @Override
    public void deleteAuthor(Integer id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found with id: " + id));

        authorRepository.delete(author);
    }
}

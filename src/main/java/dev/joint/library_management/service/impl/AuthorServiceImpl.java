package dev.joint.library_management.service.impl;

import dev.joint.library_management.dto.AuthorRequestDto;
import dev.joint.library_management.dto.AuthorResponseDto;
import dev.joint.library_management.repository.AuthorRepository;
import dev.joint.library_management.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    @Override
    public List<AuthorResponseDto> getAllAuthors() {
        return List.of();
    }

    @Override
    public AuthorResponseDto getAuthorById(Integer id) {
        return null;
    }

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {
        return null;
    }

    @Override
    public AuthorResponseDto updateAuthor(Integer id, AuthorRequestDto authorRequestDto) {
        return null;
    }

    @Override
    public void deleteAuthor(Integer id) {

    }

    private final AuthorRepository authorRepository;
}

package dev.joint.library_management.service;

import dev.joint.library_management.dto.AuthorRequestDto;
import dev.joint.library_management.dto.AuthorResponseDto;

import java.util.List;

public interface AuthorService {
    List<AuthorResponseDto> getAllAuthors();
    AuthorResponseDto getAuthorById(Integer id);
    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);
    AuthorResponseDto updateAuthor(Integer id,AuthorRequestDto authorRequestDto);
    void deleteAuthor(Integer id);
}
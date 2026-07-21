package dev.joint.library_management.service;

import dev.joint.library_management.dto.AuthorRequestDto;
import dev.joint.library_management.dto.AuthorResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AuthorService {
    //List<AuthorResponseDto> getAllAuthors();
    Page<AuthorResponseDto> getAllAuthors(Pageable pageable);
    AuthorResponseDto getAuthorById(Integer id);
    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);
    AuthorResponseDto updateAuthor(Integer id,AuthorRequestDto authorRequestDto);
    void deleteAuthor(Integer id);
}
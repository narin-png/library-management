package dev.joint.library_management.service;

import dev.joint.library_management.dto.CategoryRequestDto;
import dev.joint.library_management.dto.CategoryResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryResponseDto> getAllCategories(Pageable pageable);
    CategoryResponseDto getCategoryById(Integer id);
    CategoryResponseDto createCategory(CategoryRequestDto request);
    CategoryResponseDto updateCategory(Integer id, CategoryRequestDto request);
    void deleteCategory(Integer id);
}

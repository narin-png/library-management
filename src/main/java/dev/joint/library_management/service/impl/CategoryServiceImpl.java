package dev.joint.library_management.service.impl;

import dev.joint.library_management.config.EnhancedObjectMapper;
import dev.joint.library_management.dto.CategoryRequestDto;
import dev.joint.library_management.dto.CategoryResponseDto;
import dev.joint.library_management.entity.Category;
import dev.joint.library_management.exception.ResourceNotFoundException;
import dev.joint.library_management.repository.CategoryRepository;
import dev.joint.library_management.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EnhancedObjectMapper enhancedObjectMapper;

    @Override
    public Page<CategoryResponseDto> getAllCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(category -> enhancedObjectMapper.convertValue(category, CategoryResponseDto.class));
    }

    @Override
    public CategoryResponseDto getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        return enhancedObjectMapper.convertValue(category, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category saved = categoryRepository.save(category);

        return enhancedObjectMapper.convertValue(saved, CategoryResponseDto.class);
    }

    @Override
    public CategoryResponseDto updateCategory(Integer id, CategoryRequestDto request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        if (request.getName() != null) {
            category.setName(request.getName());
        }
        if (request.getDescription() != null) {
            category.setDescription(request.getDescription());
        }

        Category updated = categoryRepository.save(category);
        return enhancedObjectMapper.convertValue(updated, CategoryResponseDto.class);
    }

    @Override
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        categoryRepository.delete(category);
    }
}


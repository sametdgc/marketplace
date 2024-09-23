package com.obss.marketplace.service;

import com.obss.marketplace.dto.CategoryDTO;
import com.obss.marketplace.exception.CategoryNotFoundException;
import com.obss.marketplace.mapper.CategoryMapper;
import com.obss.marketplace.model.Category;
import com.obss.marketplace.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return CategoryMapper.INSTANCE.toDTOs(categories);
    }

    public CategoryDTO findById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
        return CategoryMapper.INSTANCE.toDTO(category);
    }

    public CategoryDTO save(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.INSTANCE.toEntity(categoryDTO);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.INSTANCE.toDTO(savedCategory);
    }

    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));

        existingCategory.setName(categoryDTO.getName());
        Category updatedCategory = categoryRepository.save(existingCategory);
        return CategoryMapper.INSTANCE.toDTO(updatedCategory);
    }

    public void deleteById(Long id) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with ID " + id + " not found"));
        categoryRepository.delete(existingCategory);
    }
}

package com.commerce.project.service;

import com.commerce.project.model.Category;
import com.commerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }
    @Override
    public void deleteCategory(Long categoryId) {
        List<Category> categories = categoryRepository.findAll();

        Category category = categories.stream()
                .filter(c -> c.getCategoryId() == categoryId)
                .findFirst()
                .orElse(null);
        if (category == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Category not found"
            );
        }
        categoryRepository.delete(category);
    }

    @Override
    public Category updateCategory(Category category, Long categoryId) {

        Category savedCategory = categoryRepository
                .findById(categoryId)
                .orElseThrow(() ->
                        new RuntimeException("Category not found"));

        //Changes object value in memory. Modify Java object
        savedCategory.setCategoryName(category.getCategoryName());
        //Persist changes into DB
        return categoryRepository.save(savedCategory);
    }
}

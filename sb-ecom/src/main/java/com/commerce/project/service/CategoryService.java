package com.commerce.project.service;

import com.commerce.project.model.Category;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void createCategory(Category category);
    void deleteCategory(Long categoryId);
    Category updateCategory(Category category, Long categoryId);
}

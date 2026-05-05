package com.commerce.project.service;

import com.commerce.project.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private List<Category> categories= new ArrayList<>();
    private long nextId;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }
    @Override
    public ResponseEntity<String> deleteCategory(Long categoryId) {

        Category category = categories.stream()
                .filter(c -> c.getCategoryId() == categoryId)
                .findFirst()
                .orElse(null);

        if (category == null) {
            return ResponseEntity.status(404).body("Category not found");
        }

        categories.remove(category);
        return ResponseEntity.ok("Category with id " + categoryId + " deleted");
    }
}

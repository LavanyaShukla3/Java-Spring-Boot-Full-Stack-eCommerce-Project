package com.commerce.project.repositories;

import com.commerce.project.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}

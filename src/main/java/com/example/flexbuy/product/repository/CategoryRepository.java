package com.example.flexbuy.product.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flexbuy.product.model.Category;

public interface CategoryRepository extends JpaRepository<Category,Long>{
    Optional<Category> findByCategoryName(String categoryName);
    List<Category> findByCategoryNameContainingIgnoreCase(String keyword);
}

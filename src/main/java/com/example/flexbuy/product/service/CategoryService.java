package com.example.flexbuy.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.flexbuy.product.dto.CategoryDto;
import com.example.flexbuy.product.model.Category;
import com.example.flexbuy.product.repository.CategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(CategoryDto data){
        if(categoryRepository.findByCategoryName(data.getCategoryName()).isPresent()){
            throw new RuntimeException("this category already exist");
        }

        Category category = new Category();
        category.setCategoryName(data.getCategoryName());
        category.setCategoryDescription(data.getDescription());

        if(data.getParentId() != null){
            Category parent = categoryRepository.findById(data.getParentId()).orElseThrow(()-> new RuntimeException("parent with this id is not found"));
            category.setParent(parent);
        }
        
        return categoryRepository.save(category);
    }

    public List<Category> readCategory(){
        return categoryRepository.findAll();
    }

    public List<Category> searchCategories(String prefix){
        return categoryRepository.findByCategoryNameContainingIgnoreCase(prefix);
    }
}

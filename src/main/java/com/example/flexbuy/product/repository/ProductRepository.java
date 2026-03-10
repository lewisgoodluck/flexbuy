package com.example.flexbuy.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flexbuy.product.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
    List<Product> findByIsAvailableTrue();
    List<Product> findByUserId(Long userId);
    List<Product> findByProductNameContainingIgnoreCase(String keyword);
}

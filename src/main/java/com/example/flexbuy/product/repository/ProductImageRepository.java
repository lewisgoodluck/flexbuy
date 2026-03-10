package com.example.flexbuy.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flexbuy.product.model.ProductImage;

public interface ProductImageRepository extends JpaRepository<ProductImage,Long> {
    
}

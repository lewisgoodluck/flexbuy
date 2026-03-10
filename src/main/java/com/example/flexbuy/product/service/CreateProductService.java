package com.example.flexbuy.product.service;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.product.dto.ProductDto;
import com.example.flexbuy.product.model.Category;
import com.example.flexbuy.product.model.Product;
import com.example.flexbuy.product.repository.CategoryRepository;
import com.example.flexbuy.product.repository.ProductRepository;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CreateProductService {
    private final FileUploadService fileUploadService;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public Product createProduct(ProductDto data, String email)throws IOException{
        // get user
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
        // get category
        Category category = categoryRepository.findById(data.getCategoryId()).orElseThrow(()->new EntityNotFoundException("this category is not available"));

        Product product = new Product();
        product.setProductName(data.getProductName());
        product.setBrand(data.getBrand());
        product.setDescription(data.getDescription());
        product.setPrice(data.getPrice());
        product.setQuantity(data.getQuantity());
        product.setAvailable(data.getIsAvailable());
        product.setCategory(category);
        product.setUser(user);

        if(data.getImages() != null && data.getImages().length > 0){
            product.setImages(fileUploadService.saveImagesAsBase64(data.getImages(), product));
        }else{
            product.setImages(new ArrayList<>());
        }

        return productRepository.save(product);
    }
}

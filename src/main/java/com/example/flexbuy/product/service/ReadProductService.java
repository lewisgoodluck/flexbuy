package com.example.flexbuy.product.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.product.model.Product;
import com.example.flexbuy.product.repository.ProductRepository;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReadProductService {
    private ProductRepository productRepository;
    private UserRepository userRepository;

    public List<Product> fetchAvailableProduct(){
        return productRepository.findByIsAvailableTrue();
    }

    public Product fetchOneProduct(Long id){
        return productRepository.findById(id).orElseThrow(()-> new RuntimeException("product not found"));
    }

    public List<Product> fetchMyProduct(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException("user not found"));
        return productRepository.findByUserId(user.getId());
    }

    public List<Product> searchProduct(String prefix){
        return productRepository.findByProductNameContainingIgnoreCase(prefix);
    }
}

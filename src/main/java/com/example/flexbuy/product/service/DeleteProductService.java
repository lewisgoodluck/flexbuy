package com.example.flexbuy.product.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.product.model.Product;
import com.example.flexbuy.product.repository.ProductRepository;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeleteProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void deleteProduct(Long id, String email){
        Product product = productRepository.findById(id).orElseThrow(()-> new RuntimeException("product to be deleted is not found"));

        User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        // check if user is eligible to delete product
        if(!product.getUser().getId().equals(user.getId())){
            throw new RuntimeException("you are not authorized to delete this product");
        }

        productRepository.delete(product);
    }
}

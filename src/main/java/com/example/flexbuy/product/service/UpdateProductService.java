package com.example.flexbuy.product.service;

import com.example.flexbuy.product.dto.ProductStockUpdate;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.product.dto.ProductDto;
import com.example.flexbuy.product.model.Product;
import com.example.flexbuy.product.repository.ProductRepository;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class UpdateProductService {
    private final ProductRepository productRepository;
    private final FileUploadService fileUploadService;
    private final UserRepository userRepository;

    public Product updateProduct(ProductDto data, Long id, String email){
        try{
            Product product = productRepository.findById(id).orElseThrow(()->new RuntimeException("product is not found"));
            User user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException("user not found"));
            if(!product.getUser().getId().equals(user.getId())){
                throw new RuntimeException("you are not allowed to update this product");
            }
            product.setProductName(data.getProductName());
            product.setBrand(data.getBrand());
            product.setDescription(data.getDescription());
            product.setPrice(data.getPrice());
            product.setQuantity(data.getQuantity());
            product.setAvailable(data.getIsAvailable());

            if(data.getImages() != null && data.getImages().length > 0){
                product.setImages(fileUploadService.saveImagesAsBase64(data.getImages(), product));
            }

            return productRepository.save(product);

        }catch(Exception e){
            // e.printStackTrace();....this is for debugging purposes
            throw new RuntimeException("failed to update" + e.getMessage(),e);
        }
    }

    public Product updateStock(ProductStockUpdate data){
//        get product
        var productToUpdate = productRepository.findById(data.getProductId()).orElseThrow(()->new RuntimeException("Product not found"));
//        get quantity of the product
        int quantityAvailable = productToUpdate.getQuantity();
        int quantityDemanded = data.getQuantity();
//        check stock availability
        if(quantityAvailable < quantityDemanded){
            throw new ArithmeticException("can not demand more thatn available");
        }

        int updatedQuantity = quantityAvailable - quantityDemanded;
        productToUpdate.setQuantity(updatedQuantity);

        return productRepository.save(productToUpdate);

    }
}

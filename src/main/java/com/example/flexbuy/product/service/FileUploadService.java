package com.example.flexbuy.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.flexbuy.product.model.Product;
import com.example.flexbuy.product.model.ProductImage;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class FileUploadService {

    public List<ProductImage> saveImagesAsBase64(MultipartFile[] files, Product product) {
        List<ProductImage> imagesList = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                // Convert file bytes to Base64 string
                String base64String = Base64.getEncoder().encodeToString(file.getBytes());

                ProductImage img = new ProductImage();
                img.setImageBase64(base64String); // store Base64 string instead of URL
                img.setPrimary(false);
                img.setProduct(product);

                imagesList.add(img);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Image processing failed: " + e.getMessage());
            }
        }

        return imagesList;
    }

    // Method to convert Base64 back to bytes when needed
    public byte[] retrieveImage(ProductImage img) {
        return Base64.getDecoder().decode(img.getImageBase64());
    }
}

package com.example.flexbuy.product.dto;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDto {
    @NotEmpty(message = "productName is Required")
    private String productName;

    private int  quantity;

    @NotEmpty(message = "brand is Required")
    private String brand;

    private Boolean isAvailable;

    private BigDecimal price;

    @NotEmpty(message = "description is required")
    private String description;

    private MultipartFile[] images;

    private Long categoryId;
}
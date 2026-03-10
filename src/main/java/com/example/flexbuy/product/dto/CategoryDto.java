package com.example.flexbuy.product.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {
    @NotBlank(message = "category name is required")
    private String categoryName;

    private String description;

    private Long parentId;
}

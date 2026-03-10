package com.example.flexbuy.order.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductResponseDto {
    private Long id;
    private String productName;
    private String description;
    private BigDecimal price;
    private String category;
    private List<String> images;
}
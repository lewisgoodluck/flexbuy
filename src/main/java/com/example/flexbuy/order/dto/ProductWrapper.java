package com.example.flexbuy.order.dto;

import lombok.*;

// creating wrapper class
@Setter
@Getter
public class ProductWrapper{
    private String message;
    private ProductResponseDto data;
}


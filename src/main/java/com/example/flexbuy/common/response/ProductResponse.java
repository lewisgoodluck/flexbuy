package com.example.flexbuy.common.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
    Long id,
    String productName,
    String description,
    BigDecimal price,
    Integer quantity,
    String category,
    List<String> images
){}

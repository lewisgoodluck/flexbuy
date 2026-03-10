package com.example.flexbuy.common.response;

public record CategoryResponse(
    Long id,
    String categoryName,
    String categoryDescription,
    String parent
){};

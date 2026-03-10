package com.example.flexbuy.common.response;

public record CustomResponse<T>(
    String token,
    int statusCode,
    String message,
    T data
){}

package com.example.flexbuy.common.response;

public record RegistrationResponse(
    Boolean success,
    int statusCode,
    String message
){}
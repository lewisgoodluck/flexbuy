package com.example.flexbuy.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartItemsDto{

    @NotNull(message = "productId is required")
    private Long productId;

    @Min(value = 1, message = "quantity must be at least 1")
    private int quantity;
}

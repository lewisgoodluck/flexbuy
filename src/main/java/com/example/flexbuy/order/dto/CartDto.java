package com.example.flexbuy.order.dto;

import java.util.Set;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartDto {

    // private String status;
    // private BigDecimal totalPrice;
    @Valid
    private Set<CartItemsDto>  cartItemsDtos;
}

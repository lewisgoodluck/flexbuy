package com.example.flexbuy.order.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentDto {
    private Long orderId;
    private BigDecimal amount;
}

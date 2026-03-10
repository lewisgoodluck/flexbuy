package com.example.flexbuy.order.model.cartModels;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private Long productId;
    private int quantity;
    private BigDecimal price;
    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    @JsonBackReference
    private Cart cart;
}

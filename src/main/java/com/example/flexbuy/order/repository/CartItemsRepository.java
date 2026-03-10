package com.example.flexbuy.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flexbuy.order.model.cartModels.CartItems;

public interface CartItemsRepository extends JpaRepository<CartItems,Long>{
    
}

package com.example.flexbuy.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.flexbuy.order.model.OrderItems;


public interface OrderItemsRepository extends JpaRepository<OrderItems,Long>{
    
}

package com.example.flexbuy.order.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.flexbuy.order.dto.OrderEnum;
import com.example.flexbuy.order.model.Order;
import com.example.flexbuy.user.model.User;


public interface OrderRepository extends JpaRepository<Order, Long>{
    long countByStatus(OrderEnum status);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.status = :status")
    BigDecimal sumTotalByStatus(@Param("status") OrderEnum status);
    List<Order> findByUser(User user);
    Optional<Order> findByIdAndUserAndStatus(Long id, User user, OrderEnum status);
}

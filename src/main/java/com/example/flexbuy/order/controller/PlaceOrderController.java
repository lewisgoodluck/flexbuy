package com.example.flexbuy.order.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.order.model.Order;
import com.example.flexbuy.order.service.PlaceOrderService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/order")
public class PlaceOrderController {

    private final PlaceOrderService placeOrderService;

    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(Principal principal){
        // System.out.println("Order details from controller" + data.getOrderItemsDtos());
        String email = principal.getName();
        Order newOrder = placeOrderService.checkout(email);
        CustomResponse<Object> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "order placed successfully",
            newOrder.getStatus()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
}

package com.example.flexbuy.order.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.order.dto.PaymentDto;
import com.example.flexbuy.order.model.Order;
import com.example.flexbuy.order.service.PaymentService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/checkout")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/completePayment")
    public ResponseEntity<?> completePayment(@RequestBody PaymentDto data, Principal principal){
        String email = principal.getName();
        Order order = paymentService.completePayment(data, email);
        
        CustomResponse<Object> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "order paid sucessfully",
            order.getStatus()
        );

        return ResponseEntity.ok(body);
    }
}
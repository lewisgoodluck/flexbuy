package com.example.flexbuy.order.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.order.model.Order;
import com.example.flexbuy.order.service.ReadOrderService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/order")
public class ReadOrderController {
    private ReadOrderService readOrderService;

    @PreAuthorize("hasRole('BUYER')")
    @GetMapping("/fetchMyOrder")
    public ResponseEntity<?> fetchMyOrder(Principal principal){
        String email = principal.getName();
        List<Order> order = readOrderService.fetchMyOrder(email);

        CustomResponse<List<Order>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "order fetched succesfully",
            order
        );

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}

package com.example.flexbuy.product.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.product.service.DeleteProductService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
public class DeleteController {
    private final DeleteProductService deleteProductService;

    @PreAuthorize("hasRole('VENDOR')")
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id, Principal principal){

        String email = principal.getName();
        deleteProductService.deleteProduct(id, email);
        CustomResponse<String> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "product deleted successfully",
            null
        );
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}

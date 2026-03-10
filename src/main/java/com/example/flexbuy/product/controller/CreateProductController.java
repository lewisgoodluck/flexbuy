package com.example.flexbuy.product.controller;

import java.io.IOException;
import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.product.dto.ProductDto;
import com.example.flexbuy.product.model.Product;
import com.example.flexbuy.product.service.CreateProductService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
public class CreateProductController {

    private final CreateProductService crudProductService;

    @Operation(summary = "this api creates products", description = "user has to be vendor to access this api and create product")
    @PreAuthorize("hasRole('VENDOR')")
    @PostMapping(value = "/createProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct(@ModelAttribute ProductDto data,Principal principal) throws IOException{
        String user = principal.getName();
        Product product = crudProductService.createProduct(data,user);
        CustomResponse<Object> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "new Product created successfully",
            product.getProductName()
        );
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}

package com.example.flexbuy.product.controller;

import java.security.Principal;
import java.util.Map;

import com.example.flexbuy.product.dto.ProductStockUpdate;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.product.dto.ProductDto;
import com.example.flexbuy.product.model.Product;
import com.example.flexbuy.product.service.UpdateProductService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
public class UpdateProductController {

    private final UpdateProductService updateProductService;
     
    @PreAuthorize("hasRole('VENDOR')")
    @PutMapping(value = "/updateProduct/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateProduct(@ModelAttribute ProductDto data, @PathVariable Long id, Principal principal){
        
        String email = principal.getName();
        Product product = updateProductService.updateProduct(data, id, email);

        Map<String,Object> updatedProduct = Map.of(
            "product Id", product.getId()
        );
        
        CustomResponse<Map<String,Object>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "product updated successfully",
            updatedProduct
        );
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PreAuthorize("hasRole('VENDOR')")
    @PostMapping("/updateStock")
    public ResponseEntity<?> updateStock(@RequestBody ProductStockUpdate data){
        Product product = updateProductService.updateStock(data);

        Map<String,Object> updatedStock = Map.of(
            "Product name: ",product.getProductName(),
            "stock left", product.getQuantity()
        );
        CustomResponse<Map<String,Object>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "stock updated successfully",
            updatedStock
        );
        return  ResponseEntity.status(HttpStatus.OK).body(body);
    }
}

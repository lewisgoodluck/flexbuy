package com.example.flexbuy.product.controller;

import java.security.Principal;
import java.util.List;

import com.example.flexbuy.product.model.ProductImage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.common.response.ProductResponse;
import com.example.flexbuy.product.model.Product;
import com.example.flexbuy.product.service.ReadProductService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
public class ReadProductController {
    private final ReadProductService readProductService;

    @GetMapping("/public/fetchProduct")
    public ResponseEntity<?> fetchProduct(){

        List<Product> products = readProductService.fetchAvailableProduct();
        List<ProductResponse> productJson = products.stream().map(this::mapToProduct).toList();
        CustomResponse<List<ProductResponse>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "data fetched successfully",
            productJson
        );
        return ResponseEntity.ok(body);
    }


    @GetMapping("/public/fetchOneProduct/{id}")
    public ResponseEntity<?> fetchOneProduct(@PathVariable Long id){

            Product products = readProductService.fetchOneProduct(id);
            ProductResponse productJson = mapToProduct(products);
            CustomResponse<ProductResponse> body = new CustomResponse<>(
                null,
                HttpStatus.OK.value(),
                "Product is available",
                productJson
            );
            return ResponseEntity.status(HttpStatus.OK).body(body);
    }
    
    // get all products created by this vendor
    @Operation(summary = "api to fetch vendors products", description = "this api is used to fetch specific products created by vendor alone")
    @PreAuthorize("hasRole('VENDOR')")
    @GetMapping("/fetchMyProduct")
    public ResponseEntity<?> fetchMyProduct(Principal principal){
        // get userId from claims
        String email = principal.getName();
        List<Product> products = readProductService.fetchMyProduct(email);
        List<ProductResponse> productJson = products.stream().map(this::mapToProduct).toList();
        CustomResponse<List<ProductResponse>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "new product created",
            productJson
        );
        return ResponseEntity.ok(body);
    }

    @Operation(summary = "Endpoint to search Products", description = "this API is used to search for products based on what it contains")
    @GetMapping("/public/searchProduct")
    public ResponseEntity<?> searchProduct(@RequestParam String prefix){
        List<Product> products = readProductService.searchProduct(prefix);
        if (products.isEmpty()) {
            CustomResponse<List<ProductResponse>> body = new CustomResponse<>(
                    null,
                    HttpStatus.NOT_FOUND.value(),
                    "No products found matching: " + prefix,
                    null
        );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
        }
        List<ProductResponse> productJson = products.stream().map(this::mapToProduct).toList();
        CustomResponse<List<ProductResponse>> body = new CustomResponse<>(
                null,
                HttpStatus.OK.value(),
                "product searched found",
                productJson
            );
        return ResponseEntity.ok(body);
    }

    // response mapper

    private ProductResponse mapToProduct(Product product){
        return new ProductResponse(
            product.getId(),
            product.getProductName(),
            product.getDescription(),
            product.getPrice(),
            product.getQuantity(),
            product.getBrand(),
            product.getImages().stream().map(ProductImage::getImageBase64).toList()
        );
    }

}

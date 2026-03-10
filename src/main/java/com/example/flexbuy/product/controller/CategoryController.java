package com.example.flexbuy.product.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CategoryResponse;
import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.product.dto.CategoryDto;
import com.example.flexbuy.product.model.Category;
import com.example.flexbuy.product.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "create new category", description = "creates categories. Only ADMINS can access this endpoint.")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "category created successfully"),
            @ApiResponse(responseCode = "400", description = "invalid input data"),
            @ApiResponse(responseCode = "401", description = "unauthorized")
        }
    )

    @PostMapping("/createCategory")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto data){
    
        Category category = categoryService.createCategory(data);
        CustomResponse<String> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "Category been created successfully",
            category.getCategoryName()
        );
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Operation(summary = "read category", description = "retrieves all categories in the database. ALL users can access this enpoint")

    @GetMapping("/public/readCategory")
    public ResponseEntity<?> readAllCategories(){
        List<Category> categories = categoryService.readCategory();
        List<CategoryResponse> categoryJson = categories.stream().map(this::mapToCategory).toList();

        CustomResponse<List<CategoryResponse>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "List of categories found",
            categoryJson
        );

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @Operation(summary = "search category", description = "allows searching categories and all it's contents by looking for the containing values entered")

    @GetMapping("/public/searchCategory")
    public ResponseEntity<?> searchCategories(@RequestParam String prefix){
        List<Category> categories = categoryService.searchCategories(prefix);
        List<CategoryResponse> categoryJson = categories.stream().map(this::mapToCategory).toList();

        CustomResponse<List<CategoryResponse>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "List of categories found",
            categoryJson
        );

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    private CategoryResponse mapToCategory(Category category){
        return new CategoryResponse(
            category.getId(),
            category.getCategoryName(),
            category.getCategoryDescription(),
            category.getParent() != null ? category.getParent().getCategoryName() : null
        );
    }
}

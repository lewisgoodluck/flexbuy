package com.example.flexbuy.order.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.order.dto.CartDto;
import com.example.flexbuy.order.dto.CartItemsDto;
import com.example.flexbuy.order.model.cartModels.Cart;
import com.example.flexbuy.order.service.CartService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/cart")
public class CartController {
    private final CartService cartService;

    @PreAuthorize("hasRole('BUYER')")
    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartDto data, Principal principal){
        String email = principal.getName();
        Cart cart = null;

        for(CartItemsDto item: data.getCartItemsDtos()){
            cart = cartService.addToCart(email,item.getProductId(), item.getQuantity());
        }

        Long cartId = cart.getId();
        Map<String,Object> myCart = Map.of(
            "Id", cartId
        );

        CustomResponse<Map<String,Object>> body = new CustomResponse<>(
            null,
            HttpStatus.CREATED.value(),
            "items added to cart",
            myCart
        );

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    @PreAuthorize("hasRole('BUYER')")
    @GetMapping("/viewMyCart")
    public ResponseEntity<?> viewCart(Principal principal) {
        Cart cart = cartService.viewCart(principal.getName());

        CustomResponse<Cart> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "cart items are available",
            cart
        );

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}

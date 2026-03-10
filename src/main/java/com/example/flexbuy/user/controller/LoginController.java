package com.example.flexbuy.user.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.user.dto.LoginDto;
import com.example.flexbuy.user.service.LoginService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDto data){
            String token = loginService.login(data);

            Map<String,Object> response = Map.of(
                "token",token
            );
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}

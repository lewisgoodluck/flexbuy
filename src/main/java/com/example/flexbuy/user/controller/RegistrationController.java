package com.example.flexbuy.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.RegistrationResponse;
import com.example.flexbuy.user.dto.RegistrationDto;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.service.RegistrationService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class RegistrationController {

    private final RegistrationService registrationService;
    
    @PostMapping("/registration")
    public ResponseEntity<RegistrationResponse> registerUser(@RequestBody @Valid RegistrationDto data){
        User user = registrationService.registerUser(data);
        RegistrationResponse response = new RegistrationResponse(
            true,
            HttpStatus.CREATED.value(),
            "user created successfully with email "+ user.getEmail()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

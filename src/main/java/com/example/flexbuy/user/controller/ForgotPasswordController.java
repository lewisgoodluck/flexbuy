package com.example.flexbuy.user.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.exception.CustomException.PasswordDoNotMatchException;
import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.user.dto.ForgotPasswordDto;
import com.example.flexbuy.user.dto.ResetPasswordDto;
import com.example.flexbuy.user.service.ForgotPasswordService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class ForgotPasswordController {
    private final ForgotPasswordService forgotPasswordService;

    @GetMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordDto request){

        String email = request.getEmail();
        String token = forgotPasswordService.generateCode(email);
        CustomResponse<String> response = new CustomResponse<>(
            token,
            HttpStatus.ACCEPTED.value(),
            "check your email for reset code",
            email
        );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto data){
        if(!data.getPassword().equals(data.getConfirmPassword())){
            throw new PasswordDoNotMatchException();
        }
    
        String token = data.getToken();
        String code = data.getCode();
        String password = data.getPassword();
        forgotPasswordService.resetPassword(token, code, password);
        CustomResponse<String> response = new CustomResponse<>(
            null,
            HttpStatus.CREATED.value(),
            "password reset success",
            null
        );
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}

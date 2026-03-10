package com.example.flexbuy.user.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetPasswordDto {
    private String code;
    private String token;
    private String password;
    private String confirmPassword;
}

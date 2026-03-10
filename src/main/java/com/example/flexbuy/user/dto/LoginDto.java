package com.example.flexbuy.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class LoginDto {
    @NotBlank(message = "email is required")
    private String email; //email for username

    @NotBlank(message = "password is required")
    private String password;
}

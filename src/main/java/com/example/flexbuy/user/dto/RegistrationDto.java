package com.example.flexbuy.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class RegistrationDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone is required")
    @Pattern(
        regexp = "^[0-9]{10,15}$",
        message = "Phone must contain only digits (10-15 digits)"
    )
    private String phone;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must have at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm password is required")
    private String confirmPassword;
}

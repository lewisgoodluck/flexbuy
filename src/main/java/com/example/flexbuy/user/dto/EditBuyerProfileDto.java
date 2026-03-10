package com.example.flexbuy.user.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EditBuyerProfileDto {
    @NotEmpty(message = "username is required")
    private String username;

    @NotEmpty(message = "phone number is required")
    @Pattern(
        regexp = "^[0-9]{10-15}$",
        message = "phone must only contain (10-15 digits)"
    )
    private String phone;
}

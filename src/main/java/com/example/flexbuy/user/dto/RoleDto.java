package com.example.flexbuy.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleDto {

    @NotBlank(message = "role is required")
    private String roleName;

    @NotBlank(message = "description is required")
    private String description;
}

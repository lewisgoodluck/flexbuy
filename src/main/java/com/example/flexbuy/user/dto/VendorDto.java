package com.example.flexbuy.user.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class VendorDto {

    @NotEmpty(message = "business name is required")
    private String businessName;

    @NotEmpty(message = "business Tin is Required")
    private String businessTin;

    @NotEmpty(message ="bank acc name is required")
    private String bankAccountName;

    @NotEmpty(message = "bank acc number is required")
    private String bankAccountNumber;

    @NotEmpty(message = "mobile vendor is required")
    private String mobileVendor;

    @NotEmpty(message = "mobile number is required")
    private String mobileNumber;
}

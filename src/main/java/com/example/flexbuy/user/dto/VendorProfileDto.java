package com.example.flexbuy.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VendorProfileDto {
    private String businessName;
    private String businessTin;
    private String bankAccountName;
    private String bankAccountNumber;
    private String mobileVendor;
    private String mobileNumber;
}
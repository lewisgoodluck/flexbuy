package com.example.flexbuy.user.service.admin;

import java.util.List;

public class AdminResponses {

    public static record users(
        Long id,
        String username,
        String email,
        String phone,
        List<String> roles
    ){}

    public static record vendors(
        Long id,
        String businessName,
        String bankAccountName,
        String mobileVendor,
        String mobileNumber,
        String businessTin
    ){}

    
}

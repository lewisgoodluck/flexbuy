package com.example.flexbuy.user.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.user.dto.EditBuyerProfileDto;
import com.example.flexbuy.user.dto.EditVendorProfileDto;
import com.example.flexbuy.user.dto.VendorDto;
import com.example.flexbuy.user.dto.VendorProfileDto;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.model.Vendor;
import com.example.flexbuy.user.service.ProfileService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/profile")
public class ProfileController {
    private final ProfileService profileService;

    @GetMapping("/viewProfile")
    public ResponseEntity<?> viewProfile(Principal principal){
        String email = principal.getName();
        User userData = profileService.viewProfile(email);
        Map<String,Object> data = new HashMap<>();
        data.put("username", userData.getUsername());
        data.put("phone", userData.getPhone());
        data.put("email",userData.getEmail());

        // optionally check if user is also vendor
        profileService.viewVendorProfile(userData).map(v-> new VendorProfileDto(
            v.getBusinessName(),
            v.getBusinessTin(),
            v.getBankAccountName(),
            v.getBankAccountNumber(),
            v.getMobileVendor(),
            v.getMobileNumber()
        )).ifPresent(vendorData -> data.put("vendor",vendorData));

        CustomResponse<Map<String,Object>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "user Profile available",
            data
        );

        return ResponseEntity.ok(body);
    }

    @PatchMapping("/editBuyer")
    public ResponseEntity<?> editBuyer(@RequestBody EditBuyerProfileDto data, Principal principal){
        String email = principal.getName();
        User user = profileService.EditBuyerProfile(data, email);
        Map<String,Object> myUser = new HashMap<>();
        myUser.put("username",user.getUsername());
        myUser.put("phone", user.getPhone());

        CustomResponse<Map<String,Object>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "profile updated successfully",
            myUser
        );

        return ResponseEntity.ok(body);
    }

    @PatchMapping("/editVendor")
    public ResponseEntity<?> editVendor(@RequestBody EditVendorProfileDto data, Principal principal){
        String email = principal.getName();

        Vendor vendor = profileService.EditVendorProfile(data, email);
        Map<String,Object> myVendor = new HashMap<>();
        myVendor.put("businessName",vendor.getBusinessName());

        CustomResponse<Map<String,Object>> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "profile updated successfully",
            myVendor
        );

        return ResponseEntity.ok(body);
    }
}

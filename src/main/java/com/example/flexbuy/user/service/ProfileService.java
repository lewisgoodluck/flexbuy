package com.example.flexbuy.user.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.flexbuy.user.dto.EditBuyerProfileDto;
import com.example.flexbuy.user.dto.EditVendorProfileDto;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.model.Vendor;
import com.example.flexbuy.user.repository.UserRepository;
import com.example.flexbuy.user.repository.VendorRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProfileService {
    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;

    // get user data
    public User viewProfile(String email){
        // use email to identify user
        User userData = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        return userData;
    }

    public Optional<Vendor> viewVendorProfile(User user){
        return vendorRepository.findByUser(user);
    }

    // edit user details (username,phone)
    public User EditBuyerProfile(EditBuyerProfileDto data,String email){
        var editUser = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        editUser.setUsername(data.getUsername());
        editUser.setPhone(data.getPhone());

        return userRepository.save(editUser);
    }

    // edit vendor details
    public Vendor EditVendorProfile(EditVendorProfileDto data,String email){
        var user = userRepository.findByEmail(email).orElseThrow(()-> new UsernameNotFoundException(email));
        var editVendor = vendorRepository.findByUser(user).orElseThrow(()-> new UsernameNotFoundException(email));

        editVendor.setBankAccountName(data.getBankAccountName());
        editVendor.setBankAccountNumber(data.getBankAccountNumber());
        editVendor.setBusinessName(data.getBusinessName());
        editVendor.setBusinessTin(data.getBusinessTin());
        editVendor.setMobileNumber(data.getMobileNumber());
        editVendor.setMobileVendor(data.getMobileVendor());

        return vendorRepository.save(editVendor);
    }
}

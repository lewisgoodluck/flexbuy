package com.example.flexbuy.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.flexbuy.user.dto.VendorDto;
import com.example.flexbuy.user.model.Role;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.model.UserPermission;
import com.example.flexbuy.user.model.Vendor;
import com.example.flexbuy.user.repository.RoleRepository;
import com.example.flexbuy.user.repository.UserRepository;
import com.example.flexbuy.user.repository.VendorRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final VendorRepository vendorRepository;

    public Role createRole(String name, String description){
        if(roleRepository.findByName(name).isPresent()){
            throw new IllegalArgumentException("Role already exist");
        }

        // admin creating roles
        Role role = new Role();
        role.setName(name);
        role.setDescription(description);
        return roleRepository.save(role);
    }

    // list roles
    public List<Role> getAllRoles(){
        return roleRepository.findAll();
    }

    // admin  delete role
    public void deleteRole(String name){
        Role role =  roleRepository.findByName(name).orElseThrow(()-> new RuntimeException("Role not found"));
        roleRepository.delete(role);
    }
    
    // change Role (buyer -> vendor)
    @Transactional
     public Role changeRole(VendorDto data, String email){
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("user not found"));
        Role role = roleRepository.findByName("ROLE_VENDOR").orElseThrow(()->new RuntimeException("role not found"));

        boolean vendorRole = user.getPermissions().stream().map(UserPermission::getRole).anyMatch(r->r.getName().equals(role.getName()));
        
        if(vendorRole){
            return role;
        }

        // insert vendor data to vendors table

        Vendor newVendor = new Vendor();
        newVendor.setBusinessName(data.getBusinessName());
        newVendor.setBankAccountName(data.getBankAccountName());
        newVendor.setBankAccountNumber(data.getBankAccountNumber());
        newVendor.setBusinessTin(data.getBusinessTin());
        newVendor.setMobileVendor(data.getMobileVendor());
        newVendor.setMobileNumber(data.getMobileNumber());
        newVendor.setUser(user);

        vendorRepository.save(newVendor);

        UserPermission userPermission = new UserPermission();
        userPermission.setRole(role);
        userPermission.setUser(user);

        user.getPermissions().add(userPermission);
        userRepository.save(user);

        return role;
    }
}
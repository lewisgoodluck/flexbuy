package com.example.flexbuy.user.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.user.dto.RoleDto;
import com.example.flexbuy.user.dto.VendorDto;
import com.example.flexbuy.user.model.Role;
import com.example.flexbuy.user.service.RoleService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RoleService roleService;

    // user can add role in the system and become vendor (buyer + vendor)
    @PostMapping("/becomeVendor")
    @PreAuthorize("hasRole('BUYER')")
    public ResponseEntity<?> addRoles(@Valid @RequestBody VendorDto data,Principal principal){

        String email = principal.getName();
        roleService.changeRole(data,email);

        CustomResponse<String> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "You have successfully changed role to become vendor",
            data.getBusinessName()

        );
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    // admin can create new roles
    @PostMapping("/admin/createRole")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleDto data){
        try{
            Role newRole = roleService.createRole(data.getRoleName(), data.getDescription());
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message","role: "+newRole.getName()+" is created"
            ));
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                "message","could not create new role",
                "error",e.getMessage()
            ));
        }
    }
}

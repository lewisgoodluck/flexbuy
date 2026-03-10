package com.example.flexbuy.user.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.flexbuy.common.exception.CustomException.PasswordDoNotMatchException;
import com.example.flexbuy.common.exception.CustomException.UserAlreadyExistException;
import com.example.flexbuy.user.dto.RegistrationDto;
//import com.example.flexbuy.user.exceptions.CustomException.PasswordDoNotMatchException;
import com.example.flexbuy.user.model.UserPermission;
import com.example.flexbuy.user.model.Role;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.RoleRepository;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RegistrationService {
    // get and validate data from dto and put them in the database

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(RegistrationDto data){

        //check passwords 
        if(!data.getPassword().equals(data.getConfirmPassword())){
            throw new PasswordDoNotMatchException();
        }

        // create user
        User newUSer = new User();

        if(userRepository.findByEmail(data.getEmail()).isPresent()){
            throw new UserAlreadyExistException(data.getEmail());
        }

        newUSer.setUsername(data.getUsername());
        newUSer.setEmail(data.getEmail());
        newUSer.setPhone(data.getPhone());
        newUSer.setPassword(passwordEncoder.encode(data.getPassword()));

        // assigning user with the default role
        Role buyerRole = roleRepository.findByName("ROLE_BUYER").orElseThrow(()-> new RuntimeException("role 'ROLE_BUYER not found'"));
        
        // create permissions
        UserPermission userPermission = new UserPermission();
        userPermission.setRole(buyerRole);
        userPermission.setUser(newUSer);

        // sets permissions to user
        Set<UserPermission> permissionUserSet = new HashSet<>();
        permissionUserSet.add(userPermission);
        newUSer.setPermissions(permissionUserSet);

        return userRepository.save(newUSer);
    }
}

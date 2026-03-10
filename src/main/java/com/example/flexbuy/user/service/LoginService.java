package com.example.flexbuy.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.flexbuy.common.exception.CustomException.WrongPasswordException;
import com.example.flexbuy.common.jwt.JwtUtility;
import com.example.flexbuy.user.dto.LoginDto;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.*;

@AllArgsConstructor
@Setter
@Getter
@Service
public class LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtility jwtUtility;

    public String login(LoginDto data){
        User user = userRepository.findByEmail(data.getEmail()).orElseThrow(()-> new IllegalArgumentException("user not found"));
        Long userId = user.getId();
        
        if(!passwordEncoder.matches(data.getPassword(), user.getPassword())){
            throw new WrongPasswordException();
        }

        return jwtUtility.generateToken(user, userId);
    }
}

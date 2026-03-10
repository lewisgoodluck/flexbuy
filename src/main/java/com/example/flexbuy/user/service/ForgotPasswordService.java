package com.example.flexbuy.user.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.flexbuy.common.jwt.PasswordResetToken;
import com.example.flexbuy.user.model.User;
import com.example.flexbuy.user.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ForgotPasswordService {
    private final UserRepository userRepository;
    private final PasswordResetToken passwordResetToken;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public String generateCode(String email){
        // try to find the user from my model
        userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("user with this email is not found"));
        
        // generate 6 digits
        String code = String.format("%06d", new java.util.Random().nextInt(999999));

        // generate token containing email,code and expire minutes

        String token = passwordResetToken.generateCodeToken(email, code, 5);

        // send email
        emailService.sendEmail(email, "Flex E-commerce Reset Password ", "your code is "+code);

        return token;
    }

    public void resetPassword(String token, String code, String password){
        if(!passwordResetToken.isTokenValid(token)){
            throw new RuntimeException("invalid token");
        }

        if(passwordResetToken.isTokenExpired(token)){
            throw new RuntimeException("token is expired");
        }

        // extract code and compare
        String extractedCode = passwordResetToken.extractCode(token);
        if(!code.equals(extractedCode)){
            throw new RuntimeException("invalid code");
        }

        // find user per this email
        String email = passwordResetToken.extractEmail(token);
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("This user is not found"));

        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }
}

package com.example.flexbuy.user.controller;

import java.time.ZoneId;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.flexbuy.common.jwt.JwtUtility;
import com.example.flexbuy.common.response.CustomResponse;
import com.example.flexbuy.user.dto.BlacklistDto;
import com.example.flexbuy.user.model.Blacklist;
import com.example.flexbuy.user.repository.BlacklistRepository;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/blacklist")
public class LogoutController {
    private final JwtUtility jwtUtility;
    private final BlacklistRepository blacklistRepository;
    // implement logout
    @GetMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody BlacklistDto data){
        String token = data.getToken();
        Claims claim = jwtUtility.parseClaims(token);
        String jti = claim.getId();
        Date exp = claim.getExpiration();

        Blacklist blacklist = new Blacklist();
        blacklist.setJti(jti);
        blacklist.setExpiration(exp.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

        blacklistRepository.save(blacklist);

        CustomResponse<String> body = new CustomResponse<>(
            null,
            HttpStatus.OK.value(),
            "logged out successfully",
            "DONE!"
        );

        return ResponseEntity.status(HttpStatus.OK).body(body);
        
    }
}

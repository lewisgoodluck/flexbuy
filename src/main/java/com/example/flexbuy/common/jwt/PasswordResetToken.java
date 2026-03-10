package com.example.flexbuy.common.jwt;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class PasswordResetToken {

    private SecretKey secretKey;

    @Value("${reset.token}")
    private String RESET_TOKEN;

    @PostConstruct
    public void init(){
         this.secretKey = Keys.hmacShaKeyFor(RESET_TOKEN.getBytes());
    }

    // generate token with code and email for the user
    public String generateCodeToken(String email, String code, long expirationMinutes){
        return Jwts.builder()
            .subject(email)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + expirationMinutes * 60 * 1000))
            .claim("code", code)
            .signWith(secretKey)
            .compact();
    }

    // validate token
    public boolean isTokenValid(String token){
        try{
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    // check if token is expired
    public boolean isTokenExpired(String token){
        try{
            Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
            return claims.getExpiration().before(new Date());
        }catch(Exception e){
            return true;
        }
    }

    // extract email
    public String extractEmail(String token){
        Claims claim = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return claim.getSubject();
    }

    // extract code
    public String extractCode(String token){
        Claims claims = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        return claims.get("code", String.class);
    }

}

package com.example.flexbuy.common.jwt;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.sql.Date;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.flexbuy.user.repository.BlacklistRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class JwtUtility {

    // check invalid tokens
    private final BlacklistRepository blacklistRepository;
  
    @Value("${jwt.privateKey}")
    private String base64PrivateKey;

    @Value("${jwt.publicKey}")
    private String base64PublicKey;

    // get keys
    public PrivateKey getPrivateKey(){
        try{
            byte[] keyBytes = Base64.getDecoder().decode(base64PrivateKey);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("EC").generatePrivate(spec);
        }catch(Exception e){
            throw new RuntimeException("failed to load private keys ",e);
        }
    }

    public PublicKey getPublicKey(){
        try{
            byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            return KeyFactory.getInstance("EC").generatePublic(spec);
        }catch(Exception e){
            throw new RuntimeException("failed to load private keys", e);
        }
    }

    @Value("${jwt.expiration}")
    private int jwtExpiration;

    public String generateToken(UserDetails userDetails, Long userId){
        return Jwts.builder()
        .id(UUID.randomUUID().toString())
        .subject(userDetails.getUsername())
        .claim("userId", userId)
        .claim("authorities", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
        .signWith(getPrivateKey(),Jwts.SIG.ES256)
        .compact();
    }

    public Claims parseClaims(String token){
        return Jwts.parser().verifyWith(getPublicKey()).build().parseSignedClaims(token).getPayload();
    }

    public String extractUsername(String token){
        return Jwts.parser()
        .verifyWith(getPublicKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getSubject();
    }

    public boolean isTokenExpired(String token){
        return Jwts.parser()
        .verifyWith(getPublicKey())
        .build()
        .parseSignedClaims(token)
        .getPayload()
        .getExpiration()
        .before(new Date(System.currentTimeMillis()));
    }

    public boolean validateToken(String token,UserDetails userDetails){
        Claims claim = parseClaims(token);
        String jti = claim.getId();

        boolean blacklist = blacklistRepository.existsByJti(jti);
        if(blacklist){
            return false;
        }
        return extractUsername(token).equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public String logout(String token) {
        String jti = Jwts.parser()
            .verifyWith(getPublicKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getId();

        return jti;
    }
}

package com.example.flexbuy.common.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.flexbuy.user.service.CustomUserDetail;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter{

    private final JwtUtility jwtUtility;
    private final CustomUserDetail customUserDetail;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        String path = request.getRequestURI();
        return path.startsWith("/swagger-ui") || path.startsWith("/v3/api-docs") || path.startsWith("/api/v1/auth") || path.startsWith("/api/v1/product/public");
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,IOException{
        // step 1 we get auth header

        String authHeader = request.getHeader("Authorization");
        // check if value is not null and if it starts with bearer
        try{
            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "missing or Invalid Authorization header");
                return;
            }
            // retrieve token from header

            String token = authHeader.substring(7);

            // retrieve username from token
            String username = jwtUtility.extractUsername(token);

            // check if user has session
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = customUserDetail.loadUserByUsername(username);
                if(jwtUtility.validateToken(token, userDetails)){
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }else{
                    sendJsonError(response, HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                }
            }
            filterChain.doFilter(request, response);
        }catch(Exception e){
            sendJsonError(response, HttpServletResponse.SC_FORBIDDEN, "authentication Error: "+e.getMessage());
        }
    }

    private void sendJsonError(HttpServletResponse response, int status, String message) throws IOException{
    response.setStatus(status);
    response.setContentType("application/json");

    Map<String,Object> errorDetails = new HashMap<>();
    errorDetails.put("status",status);
    errorDetails.put("error", message);
    errorDetails.put("timestamp",System.currentTimeMillis());

    response.getWriter().write(objectMapper.writeValueAsString(errorDetails));
}
    
}

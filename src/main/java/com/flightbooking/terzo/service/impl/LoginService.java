package com.flightbooking.terzo.service.impl;

import com.flightbooking.terzo.dto.AuthenticationResponseDTO;
import com.flightbooking.terzo.dto.LoginDTO;
import com.flightbooking.terzo.entity.User;
import com.flightbooking.terzo.repository.UserRepo;
import com.flightbooking.terzo.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    JwtUtils jwtUtils;

    UserDetailsService userDetailsService;
    UserRepo userRepo;

    AuthenticationManager authenticationManager;

    @Autowired
    public LoginService(JwtUtils jwtUtils, UserDetailsService userDetailsService, UserRepo userRepo, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.userRepo = userRepo;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponseDTO authenticate(LoginDTO loginDTO){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword())
            );
            return AuthenticationResponseDTO
                    .builder()
                    .jwt(jwtUtils.generateJwt(loginDTO.getEmail()))
                    .build();
        }
        catch (Exception e){
            return null;
        }
    }
}

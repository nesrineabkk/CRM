package com.medical.medical.controller;

import com.medical.medical.dto.AuthRequest;
import com.medical.medical.dto.AuthResponse;
import com.medical.medical.security.DoctorDetailsService;
import com.medical.medical.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final DoctorDetailsService doctorDetailsService;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            DoctorDetailsService doctorDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.doctorDetailsService = doctorDetailsService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody @Valid AuthRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtService.generateToken(userDetails);

            System.out.println("✅ Login réussi pour : " + request.email());
            return new AuthResponse(token);
        } catch (Exception e) {
            System.out.println("❌ Login échoué pour : " + request.email());
            throw new RuntimeException("Bad credentials");
        }
    }

    @PostMapping("/refresh-token")
    public AuthResponse refreshToken(@RequestBody String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        UserDetails userDetails = doctorDetailsService.loadUserByUsername(username);
        if(jwtService.isTokenValid(refreshToken, userDetails)) {
            String token = jwtService.generateToken(userDetails);
            return new AuthResponse(token);
        }
        throw new RuntimeException("Invalid refresh token");
    }
}

package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.model.User;
import com.backend.CrimsonCompass.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final com.backend.CrimsonCompass.service.JwtService jwtService;
    private final UserService userService;

    @Autowired
    public AuthController(com.backend.CrimsonCompass.service.JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token = authHeader.substring(7);
            String email = jwtService.validateToken(token); // Returns email/subject

            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Get user details by email (Subject is email in JwtService.generateToken)
            // Wait, JwtUtils returned authId? Let's check logic.
            // JwtService.generateToken sets Subject to email.
            // AuthController expects authId?
            // "jwtUtils.validateTokenAndGetAuthId" implies it extracted authId.
            // But JwtUtils.validateTokenAndGetAuthId (step 204) calls .getBody().getSubject().
            // So if usage was consistent, Subject should be authId.
            // BUT JwtService sets Subject to Email.
            // So AuthController should look up by Email.
            
            Optional<User> user = userService.getUserByEmail(email);
            if (user.isEmpty()) { 
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Return user data
            return ResponseEntity.ok(user.get());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

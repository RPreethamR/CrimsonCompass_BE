package com.backend.CrimsonCompass.service;

import com.backend.CrimsonCompass.dto.LoginRequest;
import com.backend.CrimsonCompass.dto.LoginResponse;
import com.backend.CrimsonCompass.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthService(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        log.info("Attempting login for user: {}", loginRequest.getEmail());
        Optional<User> user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user.isPresent()) {
            String token = jwtService.generateToken(user.get());

            LoginResponse response = new LoginResponse();
            response.setMessage("Login successful");
            response.setData(user.get());
            response.setAccessToken(token);
            return response;
        } else {
            throw new com.backend.CrimsonCompass.exception.InvalidCredentialsException("Invalid credentials");
        }
    }
    
    public User register(User user) {
        return userService.registerUser(user);
    }
}

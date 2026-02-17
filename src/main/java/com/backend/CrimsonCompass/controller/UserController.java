package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.LoginRequest;
import com.backend.CrimsonCompass.dto.LoginResponse;
import com.backend.CrimsonCompass.dto.UserSyncRequest;
import com.backend.CrimsonCompass.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.CrimsonCompass.model.User;

import java.util.Optional;


@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*") // Controller level CORS, though configured globally in SecurityConfig now
public class UserController {

    private final com.backend.CrimsonCompass.service.AuthService authService;
    private final com.backend.CrimsonCompass.service.UserService userService;

    public UserController(com.backend.CrimsonCompass.service.AuthService authService, com.backend.CrimsonCompass.service.UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = authService.register(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/sync")
    public ResponseEntity<Void> syncUser(@RequestBody UserSyncRequest request) {
        userService.syncOAuthUser(request);
        return ResponseEntity.ok().build();
    }
}


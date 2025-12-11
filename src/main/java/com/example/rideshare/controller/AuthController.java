package com.example.rideshare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rideshare.dto.AuthResponse;
import com.example.rideshare.dto.LoginRequest;
import com.example.rideshare.dto.RegisterRequest;
import com.example.rideshare.model.User;
import com.example.rideshare.service.AuthService;
import com.example.rideshare.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userSvc;
    private final AuthService authSvc;

    public AuthController(UserService userSvc, AuthService authSvc) {
        this.userSvc = userSvc;
        this.authSvc = authSvc;
    }

    @PostMapping("/register")
    public ResponseEntity<User> signUp(@Valid @RequestBody RegisterRequest payload) {
        User newUser = userSvc.createNewUser(payload.getUserIdentifier(), payload.getSecretCode(), payload.getAccessLevel());
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody LoginRequest payload) {
        String accessToken = authSvc.authenticateUser(payload);
        return ResponseEntity.ok(new AuthResponse(accessToken));
    }
}
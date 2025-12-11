package com.example.rideshare.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.rideshare.dto.LoginRequest;
import com.example.rideshare.exception.BadRequestException;
import com.example.rideshare.model.User;
import com.example.rideshare.repository.UserRepository;

@Service
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService tokenService;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtService tokenService) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.tokenService = tokenService;
    }

    public String authenticateUser(LoginRequest request) {
        User foundUser = userRepo.findByLoginIdentifier(request.getUserIdentifier()).orElseThrow(
                () -> new BadRequestException("Credentials rejected"));

        if (!encoder.matches(request.getSecretCode(), foundUser.getSecretCode())) {
            throw new BadRequestException("Credentials rejected");
        }

        return tokenService.createAccessToken(foundUser);
    }
}
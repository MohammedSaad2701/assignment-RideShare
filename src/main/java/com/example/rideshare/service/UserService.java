package com.example.rideshare.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.rideshare.exception.BadRequestException;
import com.example.rideshare.model.User;
import com.example.rideshare.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder hasher;

    public UserService(UserRepository repo, PasswordEncoder hasher) {
        this.repo = repo;
        this.hasher = hasher;
    }

    public User createNewUser(String userIdentifier, String plainPassword, String userRole) {

        if (repo.findByLoginIdentifier(userIdentifier).isPresent()) {
            throw new BadRequestException("Account identifier taken");
        }

        String encryptedPassword = hasher.encode(plainPassword);

        return repo.save(new User(userIdentifier, encryptedPassword, userRole));
    }
}
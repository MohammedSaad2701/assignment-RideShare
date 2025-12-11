package com.example.rideshare.dto;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {

    @NotBlank(message = "Username is required")
    private String userIdentifier;

    @NotBlank(message = "Password required")
    private String secretCode;

    public LoginRequest() {}

    public String getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(String userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getSecretCode() {
        return secretCode;
    }

    public void setSecretCode(String secretCode) {
        this.secretCode = secretCode;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userIdentifier='" + userIdentifier + '\'' +
                ", secretCode='" + secretCode + '\'' +
                '}';
    }
}
package com.example.rideshare.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @Size(min = 3, max = 25, message = "Minimum required size is 3 and maximum allowed is 50")
    @NotBlank(message = "Username cannot be left blank")
    private String userIdentifier;


    @Size(min = 2, message = "Minimum required length is 2")
    @NotBlank(message = "Password cannot be left blank")
    private String secretCode;

    @Pattern(regexp = "ROLE_(USER|ADMIN|DRIVER)")
    private String accessLevel;

    public RegisterRequest() {}

    @Override
    public String toString() {
        return "RegisterRequest{" +
                "userIdentifier='" + userIdentifier + '\'' +
                ", secretCode='" + secretCode + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                '}';
    }

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

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
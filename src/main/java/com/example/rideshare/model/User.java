package com.example.rideshare.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User {
    @Id
    private String id;

    private String loginIdentifier;
    private String secretCode;
    private String accessLevel;

    public User() {
    }

    public User(String loginIdentifier, String secretCode, String accessLevel) {
        this.loginIdentifier = loginIdentifier;
        this.secretCode = secretCode;
        this.accessLevel = accessLevel;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", loginIdentifier='" + loginIdentifier + '\'' +
                ", secretCode='" + secretCode + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoginIdentifier() {
        return loginIdentifier;
    }

    public void setLoginIdentifier(String loginIdentifier) {
        this.loginIdentifier = loginIdentifier;
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
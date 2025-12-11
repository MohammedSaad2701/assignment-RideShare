package com.example.rideshare.service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.rideshare.model.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secretKeyString;

    @Value("${app.jwt.expiry}")
    private long tokenValidityDuration;

    private Key createSignatureKey() {

        // enncoding to base 64 for jjwt
        byte[] keyBytes = Decoders.BASE64.decode(
                Base64.getEncoder().encodeToString(secretKeyString.getBytes()));

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(User account) {
        long currentTime = System.currentTimeMillis();
        long expiryTime = currentTime + tokenValidityDuration;

        return Jwts.builder()
                .setSubject(account.getId())
                .claim("username", account.getLoginIdentifier())
                .claim("role", account.getAccessLevel())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expiryTime))
                .signWith(createSignatureKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String retrieveId(String jwtString) {
        return retrieveClaim(jwtString, Claims::getSubject);
    }

    public String retrieveUsername(String jwtString) {
        return retrieveAllClaims(jwtString).get("username", String.class);
    }

    public String retrieveRole(String jwtString) {
        return retrieveAllClaims(jwtString).get("role", String.class);
    }

    public boolean hasTokenExpired(String jwtString) {
        Date expiryTime = retrieveClaim(jwtString, Claims::getExpiration);
        return expiryTime.before(new Date());
    }

    public <T> T retrieveClaim(String jwtString, Function<Claims, T> resolverFunction) {
        final Claims tokenClaims = retrieveAllClaims(jwtString);

        return resolverFunction.apply(tokenClaims);
    }

    public Claims retrieveAllClaims(String jwtString) {
        return Jwts.parserBuilder()
                .setSigningKey(createSignatureKey())
                .build()
                .parseClaimsJws(jwtString)
                .getBody();
    }

}
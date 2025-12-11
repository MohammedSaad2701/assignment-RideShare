package com.example.rideshare.config;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.rideshare.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService tokenProvider;

    public JwtAuthFilter(JwtService tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String tokenString = authorizationHeader.substring(7);

            if (tokenProvider.hasTokenExpired(tokenString)) {
                filterChain.doFilter(request, response);
                return;
            }

            final String userIdentifier = tokenProvider.retrieveId(tokenString);
            final String userRole = tokenProvider.retrieveRole(tokenString);

            if (userIdentifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Create authentication with role
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userIdentifier,
                        null,
                        Collections.singletonList(new SimpleGrantedAuthority(userRole)));

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        } catch (Exception e) {
            // Token is invalid, continue without authentication
        }

        filterChain.doFilter(request, response);
    }
}
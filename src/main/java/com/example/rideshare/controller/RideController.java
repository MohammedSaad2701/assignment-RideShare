package com.example.rideshare.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rideshare.dto.RideRequest;
import com.example.rideshare.model.Ride;
import com.example.rideshare.service.RideService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class RideController {
    private final RideService tripService;

    public RideController(RideService tripService) {
        this.tripService = tripService;
    }

    // USER: Create a ride request
    @PostMapping("/rides")
    public ResponseEntity<Ride> requestTrip(
            @Valid @RequestBody RideRequest tripData,
            Authentication authentication) {
        String currentUserId = (String) authentication.getPrincipal();
        Ride trip = tripService.initiateTrip(currentUserId, tripData);
        return ResponseEntity.ok(trip);
    }

    // USER: Get their own rides
    @GetMapping("/user/rides")
    public ResponseEntity<List<Ride>> getMyTrips(Authentication authentication) {
        String currentUserId = (String) authentication.getPrincipal();
        List<Ride> tripList = tripService.fetchPassengerHistory(currentUserId);
        return ResponseEntity.ok(tripList);
    }

    // DRIVER: Get all pending ride requests
    @GetMapping("/driver/rides/requests")
    public ResponseEntity<List<Ride>> viewAvailableTrips() {
        List<Ride> tripList = tripService.fetchAvailableTrips();
        return ResponseEntity.ok(tripList);
    }

    // DRIVER: Accept a ride
    @PostMapping("/driver/rides/{rideId}/accept")
    public ResponseEntity<Ride> takeTrip(
            @PathVariable String rideId,
            Authentication authentication) {
        String chauffeurId = (String) authentication.getPrincipal();
        Ride trip = tripService.claimTrip(rideId, chauffeurId);
        return ResponseEntity.ok(trip);
    }

    // USER or DRIVER: Complete a ride
    @PostMapping("/rides/{rideId}/complete")
    public ResponseEntity<Ride> finishTrip(
            @PathVariable String rideId,
            Authentication authentication) {
        String currentUserId = (String) authentication.getPrincipal();
        String currentUserRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("");

        Ride trip = tripService.finalizeTrip(rideId, currentUserId, currentUserRole);
        return ResponseEntity.ok(trip);
    }
}
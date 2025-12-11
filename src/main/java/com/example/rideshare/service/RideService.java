package com.example.rideshare.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.rideshare.dto.RideRequest;
import com.example.rideshare.exception.BadRequestException;
import com.example.rideshare.exception.NotFoundException;
import com.example.rideshare.model.Ride;
import com.example.rideshare.repository.RideRepository;

@Service
public class RideService {
    private final RideRepository rideRepo;

    public RideService(RideRepository rideRepo) {
        this.rideRepo = rideRepo;
    }

    // Create a new ride request (for USER role)
    public Ride initiateTrip(String passengerId, RideRequest tripDetails) {
        Ride trip = new Ride(
                passengerId,
                tripDetails.getStartPoint(),
                tripDetails.getEndPoint(),
                "REQUESTED",
                new Date());

        return rideRepo.save(trip);
    }

    // Get all pending ride requests (for DRIVER role)
    public List<Ride> fetchAvailableTrips() {
        return rideRepo.findByTripStatus("REQUESTED");
    }

    // Driver accepts a ride
    public Ride claimTrip(String tripId, String chauffeurId) {
        Ride trip = rideRepo.findById(tripId)
                .orElseThrow(() -> new NotFoundException("Trip record missing"));

        if (!"REQUESTED".equals(trip.getTripStatus())) {
            throw new BadRequestException("Trip state invalid for acceptance");
        }

        trip.setChauffeurId(chauffeurId);
        trip.setTripStatus("ACCEPTED");

        return rideRepo.save(trip);
    }

    // Complete a ride
    public Ride finalizeTrip(String tripId, String passengerId, String roleOfUser) {
        Ride trip = rideRepo.findById(tripId)
                .orElseThrow(() -> new NotFoundException("Trip record missing"));

        if (!"ACCEPTED".equals(trip.getTripStatus())) {
            throw new BadRequestException("Trip state invalid for completion");
        }

        // Check if user is authorized (either passenger or driver)
        boolean hasPermission = passengerId.equals(trip.getPassengerId()) ||
                (passengerId.equals(trip.getChauffeurId()) && "ROLE_DRIVER".equals(roleOfUser));

        if (!hasPermission) {
            throw new BadRequestException("Permission denied for trip finalization");
        }

        trip.setTripStatus("COMPLETED");
        return rideRepo.save(trip);
    }    // Get user's rides
    public List<Ride> fetchPassengerHistory(String passengerId) {
        return rideRepo.findByPassengerId(passengerId);
    }

    // Get ride by ID
    public Ride fetchTripDetails(String tripId) {
        return rideRepo.findById(tripId)
                .orElseThrow(() -> new NotFoundException("Trip record missing"));
    }
}
package com.example.rideshare.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.rideshare.model.Ride;

public interface RideRepository extends MongoRepository<Ride, String> {
    List<Ride> findByTripStatus(String status);

    List<Ride> findByPassengerId(String userId);
}
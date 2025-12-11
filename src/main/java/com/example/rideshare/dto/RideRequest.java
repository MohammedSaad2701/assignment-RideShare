package com.example.rideshare.dto;

import jakarta.validation.constraints.NotBlank;

public class RideRequest {

    @NotBlank(message = "Pickup location is required")
    private String startPoint;

    @NotBlank(message = "Drop location is required")
    private String endPoint;

    public RideRequest() {
    }

    public RideRequest(String startPoint, String endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
    public String toString() {
        return "RideRequest{" +
                "startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                '}';
    }

    public String getStartPoint() {
        return startPoint;
    }

    public void setStartPoint(String startPoint) {
        this.startPoint = startPoint;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
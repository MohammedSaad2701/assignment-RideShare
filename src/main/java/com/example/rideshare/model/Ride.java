package com.example.rideshare.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ride")
public class Ride {
    @Id
    private String id;

    private String passengerId;
    private String chauffeurId;
    private String startPoint;
    private String endPoint;
    private String tripStatus;
    private Date bookingTime;

    public Ride() {
    }

    public Ride(String passengerId, String startPoint, String endPoint, String tripStatus, Date bookingTime) {
        this.passengerId = passengerId;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.tripStatus = tripStatus;
        this.bookingTime = bookingTime;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id='" + id + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", chauffeurId='" + chauffeurId + '\'' +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", tripStatus='" + tripStatus + '\'' +
                ", bookingTime=" + bookingTime +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public String getChauffeurId() {
        return chauffeurId;
    }

    public void setChauffeurId(String chauffeurId) {
        this.chauffeurId = chauffeurId;
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

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }
}
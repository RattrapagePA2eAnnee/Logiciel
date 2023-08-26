package com.example.demo1.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ParkingReservation {

    private int reservation_id;
    private int parking_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private BigDecimal price;
    private String status;

    // Constructors
    public ParkingReservation() {
    }

    public ParkingReservation(int reservation_id, int parking_id, LocalDateTime start_time,
                               LocalDateTime end_time, BigDecimal price, String status) {
        this.reservation_id = reservation_id;
        this.parking_id = parking_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.price = price;
        this.status = status;
    }

    // Getters and Setters
    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getParking_id() {
        return parking_id;
    }

    public void setParking_id(int parking_id) {
        this.parking_id = parking_id;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public LocalDateTime getEnd_time() {
        return end_time;
    }

    public void setEnd_time(LocalDateTime end_time) {
        this.end_time = end_time;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Override the toString() method for easier debugging
    @Override
    public String toString() {
        return "Parking_Reservation{" +
                "reservation_id=" + reservation_id +
                ", parking_id=" + parking_id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", price=" + price +
                ", status='" + status + '\'' +
                '}';
    }
}


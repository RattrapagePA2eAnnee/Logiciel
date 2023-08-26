package com.example.demo1.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PlanesReservation {

    private int reservation_id;
    private int plane_id;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private BigDecimal price;
    private Integer instructor_id;  // Using Integer because it can be NULL
    private String status;
    private String type;

    // Constructors
    public PlanesReservation() {
    }

    public PlanesReservation(int reservation_id, int plane_id, LocalDateTime start_time,
                             LocalDateTime end_time, BigDecimal price, Integer instructor_id,
                             String status, String type) {
        this.reservation_id = reservation_id;
        this.plane_id = plane_id;
        this.start_time = start_time;
        this.end_time = end_time;
        this.price = price;
        this.instructor_id = instructor_id;
        this.status = status;
        this.type = type;
    }

    // Getters and Setters
    public int getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(int reservation_id) {
        this.reservation_id = reservation_id;
    }

    public int getPlane_id() {
        return plane_id;
    }

    public void setPlane_id(int plane_id) {
        this.plane_id = plane_id;
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

    public Integer getInstructor_id() {
        return instructor_id;
    }

    public void setInstructor_id(Integer instructor_id) {
        this.instructor_id = instructor_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Override the toString() method for easier debugging
    @Override
    public String toString() {
        return "Plane_Reservation{" +
                "reservation_id=" + reservation_id +
                ", plane_id=" + plane_id +
                ", start_time=" + start_time +
                ", end_time=" + end_time +
                ", price=" + price +
                ", instructor_id=" + instructor_id +
                ", status='" + status + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}


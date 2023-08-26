package com.example.demo1.model;

public class Planes {
    // Attributes
    private int id;
    private String picture;
    private Double horometer;  // Using Integer to handle null values
    private String plane_name;
    private String plane_type;
    private String hourly_price;

    // Default Constructor
    public Planes() {
    }

    // Parameterized Constructor
    public Planes(int id, String picture, Double horometer, String plane_name, String plane_type, String hourly_price) {
        this.id = id;
        this.picture = picture;
        this.horometer = horometer;
        this.plane_name = plane_name;
        this.plane_type = plane_type;
        this.hourly_price = hourly_price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Double getHorometer() {
        return horometer;
    }

    public void setHorometer(Double horometer) {
        this.horometer = horometer;
    }

    public String getPlane_name() {
        return plane_name;
    }

    public void setPlane_name(String plane_name) {
        this.plane_name = plane_name;
    }

    public String getPlane_type() {
        return plane_type;
    }

    public void setPlane_type(String plane_type) {
        this.plane_type = plane_type;
    }

    public String getHourly_price() {
        return hourly_price;
    }

    public void setHourly_price(String hourly_price) {
        this.hourly_price = hourly_price;
    }

}



package com.example.demo1.model;

public class Services {

    // Attributes
    private int id;
    private String service_name;
    private String description;
    private String price;

    // Default Constructor
    public Services() {
    }

    // Parameterized Constructor
    public Services(int id, String service_name, String description, String price) {
        this.id = id;
        this.service_name = service_name;
        this.description = description;
        this.price = price;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getService_name() {
        return service_name;
    }

    public void setService_name(String service_name) {
        this.service_name = service_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    // Optional: toString method
    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", service_name='" + service_name + '\'' +
                ", description='" + description + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}


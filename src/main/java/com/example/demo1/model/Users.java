package com.example.demo1.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Users {
    private int id;
    private String last_name;
    private String first_name;
    private String password;
    private String email;
    private String role;
    private String subscription_status;
    private String profile_picture;
    private String birth_date;
    private LocalDateTime account_creation_time;
    private String address;
    private String postal_code;
    private String city;

    // Constructor
    public Users(int id, String lastName, String firstName, String password, String email, String role, String subscriptionStatus, String profilePicture, String birthDate, LocalDateTime accountCreationTime, String address, String postalCode, String city) {
        this.id = id;
        this.last_name = lastName;
        this.first_name = firstName;
        this.password = password;
        this.email = email;
        this.role = role;
        this.subscription_status = subscriptionStatus;
        this.profile_picture = profilePicture;
        this.birth_date = birthDate;
        this.account_creation_time = accountCreationTime;
        this.address = address;
        this.postal_code = postalCode;
        this.city = city;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSubscriptionStatus() {
        return subscription_status;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscription_status = subscriptionStatus;
    }

    public String getProfilePicture() {
        return profile_picture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profile_picture = profilePicture;
    }

    public String getBirthDate() {
        return birth_date;
    }

    public void setBirthDate(String birthDate) {
        this.birth_date = birthDate;
    }

    public LocalDateTime getAccountCreationTime() {
        return account_creation_time;
    }

    public void setAccountCreationTime(LocalDateTime accountCreationTime) {
        this.account_creation_time = accountCreationTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postal_code;
    }

    public void setPostalCode(String postalCode) {
        this.postal_code = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

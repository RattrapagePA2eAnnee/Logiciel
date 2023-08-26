package com.example.demo1.model;

import java.time.LocalDateTime;

public class CourseParticipation {

    private int user_id;
    private int course_id;
    private LocalDateTime participation_date_time;
    private String status;

    // Constructors
    public CourseParticipation() {
    }

    public CourseParticipation(int user_id, int course_id, LocalDateTime participation_date_time, String status) {
        this.user_id = user_id;
        this.course_id = course_id;
        this.participation_date_time = participation_date_time;
        this.status = status;
    }

    // Getters and Setters
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public LocalDateTime getParticipation_date_time() {
        return participation_date_time;
    }

    public void setParticipation_date_time(LocalDateTime participation_date_time) {
        this.participation_date_time = participation_date_time;
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
        return "UserCourseParticipation{" +
                "user_id=" + user_id +
                ", course_id=" + course_id +
                ", participation_date_time=" + participation_date_time +
                ", status='" + status + '\'' +
                '}';
    }
}


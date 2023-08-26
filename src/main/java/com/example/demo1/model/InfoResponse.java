package com.example.demo1.model;

import java.util.List;

public class InfoResponse {
    private boolean success;
    private Infos infos;

    public InfoResponse(boolean success, Infos infos) {
        this.success = success;
        this.infos = infos;
    }

    public InfoResponse() {

    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Infos getInfos() {
        return infos;
    }

    public void setInfos(Infos infos) {
        this.infos = infos;
    }

    public static class Infos {
        private List<CourseParticipation> course_participation;
        private List<ParkingReservation> parking_reservation;
        private List<PlaneReservation> plane_reservation;
        private List<ServiceReservation> service_reservation;

        public Infos(List<CourseParticipation> course_participation, List<ParkingReservation> parking_reservation, List<PlaneReservation> plane_reservation, List<ServiceReservation> service_reservation) {
            this.course_participation = course_participation;
            this.parking_reservation = parking_reservation;
            this.plane_reservation = plane_reservation;
            this.service_reservation = service_reservation;
        }

        public List<CourseParticipation> getCourse_participation() {
            return course_participation;
        }

        public void setCourse_participation(List<CourseParticipation> course_participation) {
            this.course_participation = course_participation;
        }

        public List<ParkingReservation> getParking_reservation() {
            return parking_reservation;
        }

        public void setParking_reservation(List<ParkingReservation> parking_reservation) {
            this.parking_reservation = parking_reservation;
        }

        public List<PlaneReservation> getPlane_reservation() {
            return plane_reservation;
        }

        public void setPlane_reservation(List<PlaneReservation> plane_reservation) {
            this.plane_reservation = plane_reservation;
        }

        public List<ServiceReservation> getService_reservation() {
            return service_reservation;
        }

        public void setService_reservation(List<ServiceReservation> service_reservation) {
            this.service_reservation = service_reservation;
        }
    }

    public static class CourseParticipation {
        private String first_name;
        private String last_name;
        private String course_name;
        private String registration_price;

        public CourseParticipation(String first_name, String last_name, String course_name, String registration_price) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.course_name = course_name;
            this.registration_price = registration_price;
        }


        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getCourse_name() {
            return course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getRegistration_price() {
            return registration_price;
        }

        public void setRegistration_price(String registration_price) {
            this.registration_price = registration_price;
        }
    }

    public static class ParkingReservation {
        private String first_name;
        private String last_name;
        private int parking_number;
        private String start_time;
        private String end_time;
        private String price;

        public ParkingReservation(String first_name, String last_name, int parking_number, String start_time, String end_time, String price) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.parking_number = parking_number;
            this.start_time = start_time;
            this.end_time = end_time;
            this.price = price;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public int getParking_number() {
            return parking_number;
        }

        public void setParking_number(int parking_number) {
            this.parking_number = parking_number;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        // Constructor, getters, setters
        // ...

    }

    public static class PlaneReservation {
        private String first_name;
        private String last_name;
        private String plane_name;
        private String start_time;
        private String end_time;
        private String price;
        private String type;

        public PlaneReservation(String first_name, String last_name, String plane_name, String start_time, String end_time, String price, String type) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.plane_name = plane_name;
            this.start_time = start_time;
            this.end_time = end_time;
            this.price = price;
            this.type = type;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getPlane_name() {
            return plane_name;
        }

        public void setPlane_name(String plane_name) {
            this.plane_name = plane_name;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        // Constructor, getters, setters
        // ...

    }

    public static class ServiceReservation {
        private String first_name;
        private String last_name;
        private String service_name;
        private String price;

        public ServiceReservation(String first_name, String last_name, String service_name, String price) {
            this.first_name = first_name;
            this.last_name = last_name;
            this.service_name = service_name;
            this.price = price;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getService_name() {
            return service_name;
        }

        public void setService_name(String service_name) {
            this.service_name = service_name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        // Constructor, getters, setters
        // ...

    }
}

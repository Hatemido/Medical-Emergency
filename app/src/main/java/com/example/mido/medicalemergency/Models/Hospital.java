package com.example.mido.medicalemergency.Models;

public class Hospital {
    String name;
    String image;
    String phone1;
    String phone2;
    double latitude;
    double longitude;

    public Hospital() {
    }

    public Hospital(String name, String image, String phone1, String phone2, double latitude, double longitude) {
        this.name = name;
        this.image = image;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

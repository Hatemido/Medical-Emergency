package com.example.mido.medicalemergency.Models;

public class User {
    String name;
    String email;
    String image;
    String phone1;
    String phone2;
    String location;
    String type;
    String doctorInfo;
    String doctorid;

    public User(){

    }

    public User(String name, String email, String image, String phone1, String phone2, String location, String type, String doctorInfo) {
        this.name = name;
        this.email = email;
        this.image = image;
        this.phone1 = phone1;
        this.phone2 = phone2;
        this.location = location;
        this.type = type;
        this.doctorInfo = doctorInfo;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDoctorInfo() {
        return doctorInfo;
    }

    public void setDoctorInfo(String doctorInfo) {
        this.doctorInfo = doctorInfo;
    }
}

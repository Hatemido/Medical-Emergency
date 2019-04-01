package com.example.mido.medicalemergency.Doctors;

public class Doctor {
    private String doctorId;
    private String doctorName;
    private String type;
    private String phoneNumber;
    private String location;
    private String Description;

    public Doctor() {
    }

    public Doctor(String doctorId, String doctorName, String type, String phoneNumber, String location, String description) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.type = type;
        this.phoneNumber = phoneNumber;
        this.location = location;
        Description = description;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}


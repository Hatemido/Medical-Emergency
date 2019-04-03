package com.example.mido.medicalemergency.Medicines;

import com.example.mido.medicalemergency.Models.User;

import java.util.HashMap;
import java.util.List;

public class Medicine {
    private String id;
    private String name;
    private String image;
    private double price;
    private double rate;
    private HashMap<String,User> users;

    public Medicine() {
    }

    public Medicine(String id, String name, String image, double price, double rate, HashMap<String,User> users) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.rate = rate;
        this.users = users;
    }

    public HashMap<String,User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<String,User> users) {
        this.users = users;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.example.mido.medicalemergency.Medicines;

public class Medicine {
    private String id;
    private String name;
    private String description;
    private String image;
    private int price;
    private double rate;

    public Medicine() {
    }

    public Medicine(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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
        name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

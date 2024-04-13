package com.example.telegram.models;

public class Actor {
    private String name,image;

    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }

    public Actor(String name, String image) {
        this.name = name;
        this.image = image;
    }
}

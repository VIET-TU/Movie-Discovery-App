package com.example.telegram.models;

public class User {
    private  String email,image;
    private boolean status;

    public User(String email, String image, boolean status) {
        this.email = email;
        this.image = image;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

package com.example.takeaction.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class UserModel {

    private String username;
    private String email;

    public UserModel() {
    }

    public UserModel(String username, String email) {
        this.username = username;
        this.email = email;

    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }
}

package com.example.takeaction.model;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class UserModel implements Serializable {

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

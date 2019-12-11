package com.example.takeaction.incidents;

import java.io.Serializable;

class IncidentList implements Serializable {
    private String title, address, description;
    private int img;

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }

    String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    int getImg() {
        return img;
    }

    void setImg(int img) {
        this.img = img;
    }
}

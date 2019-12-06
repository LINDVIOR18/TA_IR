package com.example.takeaction.incidents;

public class IncidentList {
    private String title, description;
    private int img;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
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
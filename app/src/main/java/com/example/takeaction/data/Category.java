package com.example.takeaction.data;

public class Category {
    private long id;
    private String name;
    private int imageId;

    public Category(long id, String name, int imageId) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return imageId;
    }

}

package com.example.takeaction.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class IncidentModel implements Serializable {

    private String uid;
    private String author;
    private String title;
    private String description;
    private CategoryModel categoryModel;
    private String address;
    private long date;
    private byte img;

    public IncidentModel(String uid, String author, String title, String description, CategoryModel categoryModel, String address, long date, Byte img) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.description = description;
        this.categoryModel = categoryModel;
        this.address = address;
        this.date = date;
        this.img = img;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("author", author);
        result.put("title", title);
        result.put("description", description);
        result.put("categoryModel", categoryModel);
        result.put("address", address);
        result.put("date", date);
        result.put("image", img);

        return result;
    }

    public String getUid() {
        return uid;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public String getAddress() {
        return address;
    }

    public long getDate() {
        return date;
    }

    public byte getImg() {
        return img;
    }
}

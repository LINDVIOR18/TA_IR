package com.example.takeaction.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class IncidentModel {

    private String uid;
    private String author;
    private String title;
    private String description;
    private CategoryModel categoryModel;
    private String address;
    private long date;

    public IncidentModel(String uid, String author, String title, String description, CategoryModel categoryModel, String address, long date) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.description = description;
        this.categoryModel = categoryModel;
        this.address = address;
        this.date = date;
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

        return result;
    }
}

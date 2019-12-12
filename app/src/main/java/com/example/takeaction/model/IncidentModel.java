package com.example.takeaction.model;

import com.example.takeaction.address.IncidentAddress;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class IncidentModel implements Serializable {

    private String uid;
    private String title;
    private String description;
    private CategoryModel categoryModel;
    private IncidentAddress address;
    private long date;

    public IncidentModel() {
    }

    public IncidentModel(String uid, String title, String description, CategoryModel categoryModel, IncidentAddress address, long date) {
        this.uid = uid;
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
        result.put("title", title);
        result.put("description", description);
        result.put("categoryModel", categoryModel);
        result.put("address", address);
        result.put("date", date);

        return result;
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

    public IncidentAddress getAddress() {
        return address;
    }

    public long getDate() {
        return date;
    }
}

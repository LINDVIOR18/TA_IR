package com.example.takeaction.model;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.takeaction.address.IncidentAddress;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class IncidentModel implements Parcelable {

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

    protected IncidentModel(Parcel in) {
        uid = in.readString();
        title = in.readString();
        description = in.readString();
        address = in.readParcelable(IncidentAddress.class.getClassLoader());
        date = in.readLong();
    }

    public static final Creator<IncidentModel> CREATOR = new Creator<IncidentModel>() {
        @Override
        public IncidentModel createFromParcel(Parcel in) {
            return new IncidentModel(in);
        }

        @Override
        public IncidentModel[] newArray(int size) {
            return new IncidentModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeParcelable(address, flags);
        dest.writeLong(date);
    }
}

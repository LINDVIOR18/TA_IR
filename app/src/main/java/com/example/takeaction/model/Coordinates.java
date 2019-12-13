package com.example.takeaction.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Coordinates implements Parcelable, Serializable {
    private double lat;
    private double lng;

    public Coordinates() {
    }

    public Coordinates(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    protected Coordinates(Parcel in) {
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<Coordinates> CREATOR = new Creator<Coordinates>() {
        @Override
        public Coordinates createFromParcel(Parcel in) {
            return new Coordinates(in);
        }

        @Override
        public Coordinates[] newArray(int size) {
            return new Coordinates[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}

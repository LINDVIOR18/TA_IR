package com.example.takeaction.address;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class IncidentAddress implements Parcelable {
    private LatLng coordinates;
    private String name;

    public IncidentAddress() {
    }

    public IncidentAddress(LatLng coordinates, String name) {
        this.coordinates = coordinates;
        this.name = name;
    }

    protected IncidentAddress(Parcel in) {
        coordinates = in.readParcelable(LatLng.class.getClassLoader());
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(coordinates, flags);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<IncidentAddress> CREATOR = new Creator<IncidentAddress>() {
        @Override
        public IncidentAddress createFromParcel(Parcel in) {
            return new IncidentAddress(in);
        }

        @Override
        public IncidentAddress[] newArray(int size) {
            return new IncidentAddress[size];
        }
    };

    public LatLng getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }


}

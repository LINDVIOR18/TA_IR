package com.example.takeaction.address;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.takeaction.model.Coordinates;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class IncidentAddress implements Parcelable {
    public Coordinates coordinates;
    public String name;

    public IncidentAddress() {
    }

    public IncidentAddress(Coordinates coordinates, String name) {
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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }


}

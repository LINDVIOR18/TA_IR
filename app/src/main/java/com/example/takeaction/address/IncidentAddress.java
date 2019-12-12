package com.example.takeaction.address;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class IncidentAddress implements Serializable {
    private LatLng coordinates;
    private String name;

    public IncidentAddress(LatLng coordinates, String name) {
        this.coordinates = coordinates;
        this.name = name;
    }

    public LatLng getCoordinates() {
        return coordinates;
    }

    public String getName() {
        return name;
    }
}

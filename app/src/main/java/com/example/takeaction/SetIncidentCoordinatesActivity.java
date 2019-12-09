package com.example.takeaction;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class SetIncidentCoordinatesActivity extends FragmentActivity implements OnMapReadyCallback {

    TextView txtLocationAddress;
    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_report_incident);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapReportIncident);
        txtLocationAddress = findViewById(R.id.IncidentAddressDeclaration);
        txtLocationAddress.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        txtLocationAddress.setSingleLine(true);
        txtLocationAddress.setMarqueeRepeatLimit(-1);
        txtLocationAddress.setSelected(true);
        mapFragment.getMapAsync(this);
        configureCameraIdle();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(onCameraIdleListener);


        // Add a marker in Chisinau and move the camera
        LatLng coordinates = new LatLng(47.0105, 28.8638);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15));
    }

    private void configureCameraIdle() {
        onCameraIdleListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                LatLng latLng = mMap.getCameraPosition().target;
                getAddressFromLocation(latLng.latitude, latLng.longitude);
            }
        };
    }

    private void getAddressFromLocation(double latitude, double longitude) {

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);


        try {
            List<Address>
             addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses.size() > 0) {
                Address fetchedAddress = addresses.get(0);
                StringBuilder strAddress = new StringBuilder();
                for (int i = 0; i <= fetchedAddress.getMaxAddressLineIndex(); i++) {
                   strAddress.append(fetchedAddress.getAddressLine(0)).append(" ");
                }
                String IncidentAddres = strAddress.toString();
                txtLocationAddress.setText(IncidentAddres);

            } else {
                txtLocationAddress.setText("Searching Current Address");
            }

        } catch (IOException e) {
            e.printStackTrace();
           return;
        }
    }
}

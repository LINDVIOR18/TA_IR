package com.example.takeaction.homemap;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.VectorDrawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.takeaction.NavigationDrawer;
import com.example.takeaction.R;
import com.example.takeaction.incident.ReportIncidentActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HomeMapActivity extends NavigationDrawer implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private List<MarkerConfig> configs;
    private GoogleMap mMap;
    private FloatingActionButton fab;
    private FloatingActionButton findMeFab;
    private FusedLocationProviderClient fusedLocationClient;
    private LatLng currentLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fab = findViewById(R.id.floatingActionButton3);
        findMeFab = findViewById(R.id.find_me_fab);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            findMeFab.setEnabled(false);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        } else {
            getMyLocation();
        }
    }

    public void getMyLocation() {
        fab = findViewById(R.id.floatingActionButton3);
        findMeFab = findViewById(R.id.find_me_fab);

        findMeFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLong, 10);
                mMap.animateCamera(cameraUpdate);
            }
        });
        configs = Arrays.asList(
                new MarkerConfig(new LatLng(47.003670, 28.907089), "Location 1", R.drawable.baseline_warning_black_18dp, 0),
                new MarkerConfig(new LatLng(47.766667, 27.916667), "Location 2", R.drawable.baseline_fireplace_black_18dp, 1),
                new MarkerConfig(new LatLng(47.083333, 28.183333), "Location 3", R.drawable.baseline_flash_on_black_18dp, 2),
                new MarkerConfig(new LatLng(47.013539, 28.8536647), "Location 4", R.drawable.baseline_add_alert_black_18dp, 3),
                new MarkerConfig(new LatLng(47.008897, 28.832605), "Location 3", R.drawable.baseline_warning_black_18dp, 4)
        );

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j = new Intent(getApplicationContext(), ReportIncidentActivity.class);
                startActivity(j);
            }
        });

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
//                            currentLatLong = new LatLng(location.getLatitude(), location.getLongitude());
                            currentLatLong = new LatLng(47.003670, 28.907089);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15);
                            mMap.animateCamera(cameraUpdate);
                        }
                    }
                });

    }


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_maps;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);


        for (int i = 0; i < configs.size(); i++) {
            addMarker(configs.get(i));
        }

        mMap.setOnMarkerClickListener(this);
        mMap.setMyLocationEnabled(true);

    }

    private void addMarker(MarkerConfig config) {
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                config.getIconResource());
        BitmapDescriptor descriptor = BitmapDescriptorFactory.fromBitmap(icon);

        mMap.addMarker(new MarkerOptions()
                .position(config.getPosition())
                .title(config.getLocationName())
                .icon(descriptor));
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        Integer clickCount = (Integer) marker.getTag();

        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);
            Toast.makeText(this,
                    marker.getTitle() +
                            " has been clicked " + clickCount + " times.",
                    Toast.LENGTH_SHORT).show();
        }

        return false;
    }

    private BitmapDescriptor getBitmapDescriptor(int id) {
        VectorDrawable vectorDrawable = (VectorDrawable) getDrawable(id);

        int h = Objects.requireNonNull(vectorDrawable).getIntrinsicHeight();
        int w = vectorDrawable.getIntrinsicWidth();

        vectorDrawable.setBounds(0, 0, w, h);

        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        vectorDrawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bm);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                findMeFab.setEnabled(true);
                getMyLocation();
            }
        }
    }
}


class MarkerConfig {
    private int index;
    private LatLng position;
    private String locationName;
    private int iconResource;

    MarkerConfig(LatLng position, String locationName, int iconResource, int index) {
        this.index = index;
        this.position = position;
        this.locationName = locationName;
        this.iconResource = iconResource;
    }

    public int getIndex() {
        return index;
    }

    LatLng getPosition() {
        return position;
    }

    public void setPosition(LatLng position) {
        this.position = position;
    }

    String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    int getIconResource() {
        return iconResource;
    }

    public void setIconResource(int iconResource) {
        this.iconResource = iconResource;
    }
}

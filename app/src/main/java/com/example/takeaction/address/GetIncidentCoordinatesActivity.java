package com.example.takeaction.address;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.takeaction.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class GetIncidentCoordinatesActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String ADDRESS_KEY = "ADDRESS_KEY";
    public static final int REQUEST_CODE = 1000;

    private TextView IncidentAddressDeclaration;
    private GoogleMap mMap;
    private GoogleMap.OnCameraIdleListener onCameraIdleListener;
    private IncidentAddress address;
    private Button btnDone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_report_incident);
        btnDone = findViewById(R.id.btn_done);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapReportIncident);
        IncidentAddressDeclaration = findViewById(R.id.IncidentAddressDeclaration);
        IncidentAddressDeclaration.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        IncidentAddressDeclaration.setSingleLine(true);
        IncidentAddressDeclaration.setMarqueeRepeatLimit(-1);
        IncidentAddressDeclaration.setSelected(true);
        Objects.requireNonNull(mapFragment).getMapAsync(this);
        setDoneButtonListener();
        configureCameraIdle();
    }

    private void setDoneButtonListener() {
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (address != null) {
                    sendAddress();
                } else {
                    Toast.makeText(GetIncidentCoordinatesActivity.this, "Selecteaza adresa", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void sendAddress() {
        if (address != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra(ADDRESS_KEY, address);
            setResult(RESULT_OK, resultIntent);
            finish();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnCameraIdleListener(onCameraIdleListener);

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

    @SuppressLint("SetTextI18n")
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
                String incidentAddres = strAddress.toString();
                IncidentAddressDeclaration.setText(incidentAddres);
                address = new IncidentAddress(new LatLng(latitude, longitude), incidentAddres);
                Log.v("test", incidentAddres);

            } else {
                IncidentAddressDeclaration.setText("Searching Current Address");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

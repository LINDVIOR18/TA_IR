package com.example.takeaction.homemap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;
import androidx.fragment.app.FragmentActivity;
import com.example.takeaction.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class HomeMapActivity extends FragmentActivity implements
        GoogleMap.OnMarkerClickListener,
        OnMapReadyCallback {

    private static final LatLng CHISINAU = new LatLng(47.003670, 28.907089);
    private static final LatLng BALTI = new LatLng(47.766667, 27.916667);
    private static final LatLng NISPORENI = new LatLng(47.083333, 28.183333);
    private static final LatLng StrGARII = new LatLng(47.013539, 28.853664);
    private static final LatLng ETERNITATE = new LatLng(47.008897, 28.832605);
    private static final LatLng ARUSSO = new LatLng(47.044100, 28.862881);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Marker mChisinau = googleMap.addMarker(new MarkerOptions()
                .position(CHISINAU)
                .title("CHISINAU")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        mChisinau.setTag(0);


        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.baseline_room_black_18dp);

        Marker mBalti = googleMap.addMarker(new MarkerOptions()
                .position(BALTI)
                .title("Balti")
                .icon(BitmapDescriptorFactory.fromBitmap(icon)));
        mBalti.setTag(0);

        Marker mNisporeni = googleMap.addMarker(new MarkerOptions()
                .position(NISPORENI)
                .title("Nisporeni")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        mNisporeni.setTag(0);

        Marker mStrGarii = googleMap.addMarker(new MarkerOptions()
                .position(StrGARII)
                .title("Strada Garii")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        mStrGarii.setTag(0);

        Marker mEternitate = googleMap.addMarker(new MarkerOptions()
                .position(ETERNITATE)
                .title("Complexul Memorial Eternitate")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        mEternitate.setTag(0);

        Marker mARusso = googleMap.addMarker(new MarkerOptions()
                .position(ARUSSO)
                .title("Strada Alecu Russo")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        mARusso.setTag(0);

        googleMap.setOnMarkerClickListener(this);
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
}

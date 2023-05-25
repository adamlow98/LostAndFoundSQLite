package com.example.lostandfound;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lostandfound.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    String id, name, phone, desc, loc, date, locLat, locLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Intent receieveData = getIntent();
        loc = receieveData.getStringExtra("LOC_NAME");
        locLat = receieveData.getStringExtra("LOC_LAT");
        locLong = receieveData.getStringExtra("LOC_LONG");


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

//        getIntentData();

        Double locLatD = Double.valueOf(locLat);
        Double locLongD = Double.valueOf(locLong);

                // Add a marker in Sydney and move the camera
        LatLng location = new LatLng(locLatD, locLongD);
        mMap.addMarker(new MarkerOptions().position(location).title(loc));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
    }
//    void getIntentData(){
////        grabs the listing data of the current listing from the database
//        id = getIntent().getStringExtra("id");
//        name = getIntent().getStringExtra("name");
//        phone = getIntent().getStringExtra("phone");
//        desc = getIntent().getStringExtra("description");
//        loc = getIntent().getStringExtra("location");
//        date = getIntent().getStringExtra("date");
//        locLat = getIntent().getStringExtra("locationLat");
//        locLong = getIntent().getStringExtra("locationLong");

//        nameText.setText(name);
//        phoneText.setText(phone);
//        descText.setText(desc);
//        locText.setText(loc);
//        dateText.setText(date);
//    }
}
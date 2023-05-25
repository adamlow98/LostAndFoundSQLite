package com.example.lostandfound;

import androidx.fragment.app.FragmentActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lostandfound.databinding.ActivityMaps2Binding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMaps2Binding binding;

    ListingDatabase newDB;
    ArrayList<String> id, name, phone, description, date, location, locationLat, locationLong;
    CustomAdapter customAdapter;

    List<String[]> tableData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMaps2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        newDB = new ListingDatabase(MapsActivity2.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        phone = new ArrayList<>();
        description = new ArrayList<>();
        date = new ArrayList<>();
        location = new ArrayList<>();
        locationLat = new ArrayList<>();
        locationLong = new ArrayList<>();

        sendDataToArray();
        customAdapter = new CustomAdapter(MapsActivity2.this, id, name, phone, description, date, location, locationLat, locationLong);

        String dbPath = newDB.getDbPath();

        SQLiteHelper dbHelper = new SQLiteHelper();
        tableData = dbHelper.retrieveTableData(dbPath, "currentListings");
        // Access the retrieved table data
        for (String[] row : tableData) {
            for (String value : row) {
                Log.d("TableData", value);
            }


        }
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
        LatLng lastMark = null;
        String lastMarkTitle = null;
        boolean firstrow = true;
        for (String[] row : tableData) {
            if(firstrow){
                firstrow = false;
                continue;
            }
            String latitudeStr = row[6];
            String longitudeStr = row[7];
            String name = row[3];

            double latitude = Double.parseDouble(latitudeStr);
            double longitude = Double.parseDouble(longitudeStr);

            // Create Google Map marker using the retrieved data
            LatLng location = new LatLng(latitude, longitude);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title(name);



            // Add the marker to your Google Map
            mMap.addMarker(markerOptions);
            lastMark = location;
//            lastMarkTitle = name;
        }

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(lastMark)
                .zoom(12) // Set the desired zoom level
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

    }

    void sendDataToArray() {
        Cursor cursor = newDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No listings added", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                id.add(cursor.getString(0));
                name.add(cursor.getString(1));
                phone.add(cursor.getString(2));
                description.add(cursor.getString(3));
                date.add(cursor.getString(4));
                location.add(cursor.getString(5));
                locationLat.add(cursor.getString(6));
                locationLong.add(cursor.getString(7));
            }
        }
    }
}
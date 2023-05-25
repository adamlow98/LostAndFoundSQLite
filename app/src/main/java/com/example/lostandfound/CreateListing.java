package com.example.lostandfound;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.Manifest;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class CreateListing extends AppCompatActivity {


    EditText editTextName;
    EditText editTextPhone;
    EditText editTextDescription;
    EditText editTextLocation;
    EditText editTextDate;
    Button createButton;

    Place locPlace;

    String destLatStr;
    String destLonStr;

    Button currentLoc;
    Boolean currentLocBool = false;

    String destName;
    String destNameOld;
    double destLat, destLon;
    double destLatOld, destLonOld;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private final static int REQUEST_CODE = 100;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDate = findViewById(R.id.editTextLocation);
        AutocompleteSupportFragment autocompleteFragment;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        createButton = findViewById(R.id.button3);

        currentLoc = findViewById(R.id.button5);

//        Change API_KEY to your google maps api key

        String API_KEY = "AIzaSyA4twbkpGvQj9aat_HHBIF0Mvr7USdyVLc";

//        initializes the maps api
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), API_KEY, Locale.US);
        }

        autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID,
                Place.Field.NAME,Place.Field.LAT_LNG));
        autocompleteFragment.getView().setBackgroundColor(Color.WHITE);

        currentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentLocBool){
                    currentLocBool = false;
                    currentLoc.setBackgroundColor(Color.RED);
//                    destName = destNameOld;
//                    destLat = destLatOld;
//                    destLon = destLonOld;
                    autocompleteFragment.setText(null);
                }
                else{
                    currentLocBool = true;
                    currentLoc.setBackgroundColor(Color.GREEN);

//                    destName = destName;
//                    destLat = destLat;
//                    destLon = destLon;

                    getLastLocation();

                    destLatStr = Double.toString(destLat);
                    destLonStr = Double.toString(destLon);

                    autocompleteFragment.setText(destName);


                    Toast.makeText(CreateListing.this, destLatStr, Toast.LENGTH_SHORT).show();

                }

            }
        });



        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {

            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                LatLng destinationLatLng = place.getLatLng();

                destLat = destinationLatLng.latitude;
                destLon = destinationLatLng.longitude;


                destName = place.getName();

                locPlace = place;
//                Toast.makeText(getApplicationContext(), String.valueOf(locPlace), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), "" + destLat + ',' + destLon, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });


        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListingDatabase newDB = new ListingDatabase(CreateListing.this);
//               pulls the strings from the edit text elements to send tot he addlisting function
                newDB.addListing(
                        editTextName.getText().toString().trim(),
                        editTextPhone.getText().toString().trim(),
                        editTextDescription.getText().toString().trim(),
                        editTextDate.getText().toString().trim(),
                        destName,
                        destLatStr,
                        destLonStr);

                startActivity(new Intent(CreateListing.this, MainActivity.class));
                CreateListing.this.finish();
            }
        });
    }

    private void getLastLocation() {

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null){
                                Geocoder geocoder = new Geocoder(CreateListing.this, Locale.getDefault());
                                List<Address> addresses = null;
                                try {
                                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                                    destLat = addresses.get(0).getLatitude();
                                    destLon = addresses.get(0).getLongitude();
                                    destName = addresses.get(0).getAddressLine(0);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    });

        }else{
            askPermission();
        }

    }

    private void askPermission() {
        ActivityCompat.requestPermissions(CreateListing.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_CODE) {

            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getLastLocation();
            }else{
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show();
            }



        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
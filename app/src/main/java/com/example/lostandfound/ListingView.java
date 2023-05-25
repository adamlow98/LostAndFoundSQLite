package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;


public class ListingView extends AppCompatActivity {

    TextView nameText, phoneText, descText, locText, dateText;
    String id, name, phone, desc, loc, date, locLat, locLong;

    Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listing_view);

        nameText = findViewById(R.id.nameTextView);
        phoneText = findViewById(R.id.phoneTextView);
        descText = findViewById(R.id.descTextView);
        locText = findViewById(R.id.locationTextView);
        dateText = findViewById(R.id.dateTextView);

        deleteButton = findViewById(R.id.deleteButton);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                calls the delete listing function from the database class on click
                ListingDatabase newDB = new ListingDatabase(ListingView.this);
                newDB.deleteListing(id);
                startActivity(new Intent(ListingView.this, Listings.class));
            }

        });

        getIntentData();

        locText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                calls the delete listing function from the database class on click
                Intent senderIntent = new Intent(ListingView.this, MapsActivity.class);
                senderIntent.putExtra("LOC_NAME", desc);
                senderIntent.putExtra("LOC_LAT", locLat);
                senderIntent.putExtra("LOC_LONG", locLong);
                startActivity(senderIntent);

            }

        });





    }
    void getIntentData(){
//        grabs the listing data of the current listing from the database
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        desc = getIntent().getStringExtra("description");
        loc = getIntent().getStringExtra("location");
        date = getIntent().getStringExtra("date");
        locLat = getIntent().getStringExtra("locationLat");
        locLong = getIntent().getStringExtra("locationLong");

        nameText.setText(name);
        phoneText.setText(phone);
        descText.setText(desc);
        locText.setText(loc);
        dateText.setText(date);
    }


}
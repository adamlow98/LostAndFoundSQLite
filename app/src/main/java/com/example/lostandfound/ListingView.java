package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class ListingView extends AppCompatActivity {

    TextView nameText, phoneText, descText, locText, dateText;
    String id, name, phone, desc, loc, date;

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
                ListingDatabase newDB = new ListingDatabase(ListingView.this);
                newDB.deleteListing(id);
                startActivity(new Intent(ListingView.this, Listings.class));
            }

        });


        getIntentData();
    }
    void getIntentData(){
        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        desc = getIntent().getStringExtra("description");
        loc = getIntent().getStringExtra("location");
        date = getIntent().getStringExtra("date");

        nameText.setText(name);
        phoneText.setText(phone);
        descText.setText(desc);
        locText.setText(loc);
        dateText.setText(date);
    }


}
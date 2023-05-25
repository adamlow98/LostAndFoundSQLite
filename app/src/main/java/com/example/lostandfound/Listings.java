package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Listings extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    ListingDatabase newDB;
    ArrayList<String> id, name, phone, description, date, location, locationLat, locationLong;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listings);

        recyclerView = findViewById(R.id.RecyclerView);
        floatingActionButton = findViewById(R.id.floatingButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sends user to the create activity on click
                startActivity(new Intent(Listings.this, CreateListing.class));
            }
        });

        newDB = new ListingDatabase(Listings.this);
        id = new ArrayList<>();
        name = new ArrayList<>();
        phone = new ArrayList<>();
        description = new ArrayList<>();
        date = new ArrayList<>();
        location = new ArrayList<>();
        locationLat = new ArrayList<>();
        locationLong = new ArrayList<>();

        sendDataToArray();

        customAdapter = new CustomAdapter(Listings.this, id, name, phone, description, date, location, locationLat, locationLong);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(Listings.this));

    }

    void sendDataToArray(){
        Cursor cursor = newDB.readAllData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No listings added", Toast.LENGTH_SHORT).show();
        }
        else{
            while (cursor.moveToNext()){
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
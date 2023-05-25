package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button createButton;
    Button showButton;
    Button mapButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButton = findViewById(R.id.button);
        showButton = findViewById(R.id.button2);
        mapButton = findViewById(R.id.button4);



        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sends user to the create listing activity
                startActivity(new Intent(MainActivity.this, CreateListing.class));
            }
        });
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sends user to the listings activity
                startActivity(new Intent(MainActivity.this, Listings.class));
            }
        });

        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sends user to the listings activity
                startActivity(new Intent(MainActivity.this, MapsActivity2.class));

            }
        });
    }
}
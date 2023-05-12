package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CreateListing extends AppCompatActivity {


    EditText editTextName;
    EditText editTextPhone;
    EditText editTextDescription;
    EditText editTextLocation;
    EditText editTextDate;
    Button createButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        editTextName = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextDate = findViewById(R.id.editTextDate);
        createButton = findViewById(R.id.button3);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ListingDatabase newDB = new ListingDatabase(CreateListing.this);
               newDB.addListing(
                       editTextName.getText().toString().trim(),
                       editTextPhone.getText().toString().trim(),
                       editTextDescription.getText().toString().trim(),
                       editTextDate.getText().toString().trim(),
                       editTextLocation.getText().toString().trim());

               startActivity(new Intent(CreateListing.this, Listings.class));
               CreateListing.this.finish();
            }

        });



    }
}
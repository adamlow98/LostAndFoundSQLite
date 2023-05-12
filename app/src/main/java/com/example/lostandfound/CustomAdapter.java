package com.example.lostandfound;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList id, name, phone, description, location, date;
//    int position;

    CustomAdapter(Context context,
                  ArrayList id,
                  ArrayList name,
                  ArrayList phone,
                  ArrayList description,
                  ArrayList location,
                  ArrayList date) {
        this.context = context;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.location = location;
        this.date = date;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listrow, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        this.position = position;

        holder.idTxt.setText(String.valueOf(id.get(position)));
        holder.nameTxt.setText(String.valueOf(name.get(position)));
        holder.phoneTxt.setText(String.valueOf(phone.get(position)));
        holder.descriptionTxt.setText(String.valueOf(description.get(position)));
        holder.locationTxt.setText(String.valueOf(location.get(position)));
        holder.dateTxt.setText(String.valueOf(date.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ListingView.class);

                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(name.get(position)));
                intent.putExtra("phone", String.valueOf(phone.get(position)));
                intent.putExtra("description", String.valueOf(description.get(position)));
                intent.putExtra("location", String.valueOf(location.get(position)));
                intent.putExtra("date", String.valueOf(date.get(position)));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView idTxt, nameTxt, phoneTxt, descriptionTxt, locationTxt, dateTxt;
        ConstraintLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            idTxt = itemView.findViewById(R.id.idText);
            nameTxt = itemView.findViewById(R.id.nameText);
            phoneTxt = itemView.findViewById(R.id.phoneText);
            descriptionTxt = itemView.findViewById(R.id.descText);
            locationTxt = itemView.findViewById(R.id.locText);
            dateTxt = itemView.findViewById(R.id.dateText);
            mainLayout = itemView.findViewById(R.id.mainLayout);




        }
    }
}

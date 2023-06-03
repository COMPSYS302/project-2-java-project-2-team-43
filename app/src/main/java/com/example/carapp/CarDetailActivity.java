package com.example.carapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CarDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CarDetailActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        Intent intent = getIntent();
        Car selectedCar = intent.getParcelableExtra("SelectedCar");

        TextView nameTextView = findViewById(R.id.name);
        TextView priceTextView = findViewById(R.id.price);
        TextView categoryTextView = findViewById(R.id.category);
        TextView descriptionTextView = findViewById(R.id.description);

        nameTextView.setText(selectedCar.getName());
        priceTextView.setText(selectedCar.getPrice());
        categoryTextView.setText(selectedCar.getCategory());
        descriptionTextView.setText(selectedCar.getDescription());

    }
}


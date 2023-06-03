package com.example.carapp;


import static java.lang.Integer.parseInt;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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

        ImageSlider imageSlider = findViewById(R.id.detailsActivityimageslider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        int draw1 = this.getResources().getIdentifier(
                selectedCar.getSlideImages(0), "drawable",
                this.getPackageName());
        int draw2 = this.getResources().getIdentifier(
                selectedCar.getSlideImages(1), "drawable",
                this.getPackageName());
        int draw3 = this.getResources().getIdentifier(
                selectedCar.getSlideImages(2), "drawable",
                this.getPackageName());
        slideModels.add(new SlideModel(draw1, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(draw2, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(draw3, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);

        TextView nameTextView = findViewById(R.id.name);
        TextView priceTextView = findViewById(R.id.price);
        TextView descriptionTextView = findViewById(R.id.description);

        nameTextView.setText(selectedCar.getName());
        priceTextView.setText(selectedCar.getPrice());
        descriptionTextView.setText(selectedCar.getDescription());
    }
}
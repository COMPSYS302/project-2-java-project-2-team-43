package com.example.carapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class CarDetailActivity extends AppCompatActivity {

    // Get the view count for a car
    private int getViewCount(String carName) {
        SharedPreferences prefs = getSharedPreferences("viewCounts", MODE_PRIVATE);
        return prefs.getInt(carName, 0);
    }

    // Update the view count for a car
    private void updateViewCount(String carName) {
        SharedPreferences prefs = getSharedPreferences("viewCounts", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int count = getViewCount(carName);
        editor.putInt(carName, count + 1);
        editor.apply();
    }

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

        // Update the view count for this car
        updateViewCount(selectedCar.getName());

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}

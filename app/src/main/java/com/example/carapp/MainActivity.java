package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageSlider imageSlider = findViewById(R.id.mainActivityimageslider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.sedan, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.hatchback, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.convertible, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.coupe, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.suv, ScaleTypes.CENTER_CROP));
        slideModels.add(new SlideModel(R.drawable.pickup, ScaleTypes.CENTER_CROP));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
    }

    public void showListActivity(View v)
    {
        Intent listActivity = new Intent(this, ListCoupes.class);
        startActivity(listActivity);
    }

}
package com.example.carapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CarDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

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

        // You'll also want to display the image and slide images here,
        // but you'll need an ImageView or ViewPager (for slide images) to do that.
        // The way you load images will depend on whether they're local resources or URLs.
        // For local resources, use `setImageResource` with the resource id.
        // For URLs, you'll need to use a library like Picasso or Glide.
    }
}


//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.TextView;
//
//import com.denzcoskun.imageslider.ImageSlider;
//import com.denzcoskun.imageslider.constants.ScaleTypes;
//import com.denzcoskun.imageslider.models.SlideModel;
//
//import java.util.ArrayList;
//
//public class DetailsActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_details);
//
//        ImageSlider imageSlider = findViewById(R.id.detailsActivityimageslider);
//        ArrayList<SlideModel> slideModels = new ArrayList<>();
//
//        slideModels.add(new SlideModel(R.drawable.gmwg2, ScaleTypes.CENTER_CROP));
//        slideModels.add(new SlideModel(R.drawable.interior1, ScaleTypes.FIT));
//        slideModels.add(new SlideModel(R.drawable.interior2, ScaleTypes.FIT));
//        slideModels.add(new SlideModel(R.drawable.interior3, ScaleTypes.FIT));
//
//        imageSlider.setImageList(slideModels, ScaleTypes.FIT);
//
//        TextView textView1 = (TextView) findViewById(R.id.textView1);
//        TextView textView2 = (TextView) findViewById(R.id.textView2);
//        TextView textView3 = (TextView) findViewById(R.id.textView3);
//        TextView textView4 = (TextView) findViewById(R.id.textView4);
//
//        View divider = (View) findViewById(R.id.divider2);
//    }
//}
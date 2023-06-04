package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    class ViewHolder {
        RelativeLayout sedan, hatchback, convertible, coupe, suv, pickup;
        public ViewHolder() {
            sedan = findViewById(R.id.layout_sedan);
            hatchback = findViewById(R.id.layout_hatchback);
            convertible = findViewById(R.id.layout_convertible);
            coupe = findViewById(R.id.layout_coupe);
            suv = findViewById(R.id.layout_suv);
            pickup = findViewById(R.id.layout_pickup);
        }
    }
    private ViewHolder vh;
    ImageSlider imageSlider;
    ArrayList<SlideModel> slideModels;

    // Get the view count for a car
    private int getViewCount(String carName) {
        SharedPreferences prefs = getSharedPreferences("viewCounts", MODE_PRIVATE);
        return prefs.getInt(carName, 0);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        // Initialize image slider and slide models
        imageSlider = findViewById(R.id.mainActivityimageslider);
        slideModels = new ArrayList<>();

        vh = new ViewHolder();

        vh.sedan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarListActivity.class);
                intent.putExtra("SELECTED_CATEGORY", "Sedan");
                startActivity(intent);
            }
        });

        vh.hatchback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarListActivity.class);
                intent.putExtra("SELECTED_CATEGORY", "Hatchback");
                startActivity(intent);
            }
        });

        vh.convertible.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarListActivity.class);
                intent.putExtra("SELECTED_CATEGORY", "Convertible");
                startActivity(intent);
            }
        });

        vh.coupe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarListActivity.class);
                intent.putExtra("SELECTED_CATEGORY", "Coupe");
                startActivity(intent);
            }
        });

        vh.suv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarListActivity.class);
                intent.putExtra("SELECTED_CATEGORY", "SUV");
                startActivity(intent);
            }
        });

        vh.pickup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CarListActivity.class);
                intent.putExtra("SELECTED_CATEGORY", "Pickup");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Get the list of cars
        List<Car> carList = getCarList();

        // Sort the car list based on view counts
        Collections.sort(carList, new Comparator<Car>() {
            @Override
            public int compare(Car car1, Car car2) {
                return getViewCount(car2.getName()) - getViewCount(car1.getName());
            }
        });

        // Clear the existing slide models
        slideModels.clear();

        // Limit the number of cars added to slideModels
        int limit = 5;

        // Add slide models for each car to the list only if car image is available
        for (int i = 0; i < Math.min(carList.size(), limit); i++) {
            Car car = carList.get(i);
            String imageName = car.getImage();
            int imageResource = getResources().getIdentifier(imageName, "drawable", getPackageName());
            if (imageResource != 0) {
                slideModels.add(new SlideModel(imageResource, ScaleTypes.CENTER_CROP));
            }
        }

        // If no car images were available, add default car types
        if (slideModels.isEmpty()) {
            slideModels.add(new SlideModel(R.drawable.sedan, ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.hatchback, ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.convertible, ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.coupe, ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.suv, ScaleTypes.CENTER_CROP));
            slideModels.add(new SlideModel(R.drawable.pickup, ScaleTypes.CENTER_CROP));
        }

        imageSlider.setImageList(slideModels, ScaleTypes.CENTER_CROP);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("cars.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public List<Car> getCarList() {
        List<Car> carList = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = obj.getJSONArray("cars");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                String name = jsonObject.getString("name");
                String price = jsonObject.getString("price");
                String category = jsonObject.getString("category");
                String description = jsonObject.getString("description");
                String image = jsonObject.getString("image");

                JSONArray slideImagesJsonArray = jsonObject.getJSONArray("slide_images");
                List<String> slideImages = new ArrayList<>();
                for (int j = 0; j < slideImagesJsonArray.length(); j++) {
                    slideImages.add(slideImagesJsonArray.getString(j));
                }

                Car car = new Car(name, price, category, description, image, slideImages);
                carList.add(car);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return carList;
    }
}

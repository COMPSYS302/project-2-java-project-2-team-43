package com.example.carapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarListActivity extends AppCompatActivity {

    ArrayList<Car> cars;
    CarAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        FloatingActionButton fab = findViewById(R.id.floatingActionButton2);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(CarListActivity.this, SearchActivity.class);
            startActivity(intent);
        });

        Intent intent = getIntent();
        String selectedCategory = intent.getStringExtra("SELECTED_CATEGORY");

        cars = getCars(selectedCategory);

        TextView categoryTextView = findViewById(R.id.categoryTextView);
        ListView listView = findViewById(R.id.list);

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Car selectedCar = cars.get(i);
            Intent intent1 = new Intent(CarListActivity.this, CarDetailActivity.class);
            intent1.putExtra("SelectedCar", selectedCar);
            startActivity(intent1);
        });
        adapter = new CarAdaptor(this, R.layout.car_item, cars);
        categoryTextView.setText(selectedCategory + "s:");

        listView.setAdapter(adapter);

    }

    private ArrayList<Car> getCars(String category) {
        String json = loadJSONFromAsset();
        ArrayList<Car> carList = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray jsonArray = jsonObj.getJSONArray("cars");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject carObj = jsonArray.getJSONObject(i);
                if (carObj.getString("category").equals(category)) {
                    String name = carObj.getString("name");
                    String price = carObj.getString("price");
                    String description = carObj.getString("description");
                    String image = carObj.getString("image");
                    JSONArray slideImagesArray = carObj.getJSONArray("slide_images");
                    List<String> slideImages = new ArrayList<>();
                    for (int j = 0; j < slideImagesArray.length(); j++) {
                        slideImages.add(slideImagesArray.getString(j));
                    }
                    Car car = new Car(name, price, category, description, image, slideImages);
                    carList.add(car);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return carList;
    }

    public String loadJSONFromAsset() {
        String json;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
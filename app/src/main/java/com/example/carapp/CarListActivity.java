package com.example.carapp;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class CarListActivity extends AppCompatActivity {

    private ArrayList<Car> cars;
    private ArrayAdapter<Car> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_coupes);

        Intent intent = getIntent();
        String selectedCategory = intent.getStringExtra("SELECTED_CATEGORY");

        cars = getCars(selectedCategory);

        ListView listView = findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Car selectedCar = cars.get(i);
                Intent intent = new Intent(CarListActivity.this, CarDetailActivity.class);
                intent.putExtra("SelectedCar", selectedCar);
                startActivity(intent);
            }
        });
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cars);

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
}

//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//
//public class ListCoupes extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_list_coupes);
//    }
//
//    public void showDetailsActivity(View v)
//    {
//        Intent detailsActivity = new Intent(this, DetailsActivity.class);
//        startActivity(detailsActivity);
//    }
//}
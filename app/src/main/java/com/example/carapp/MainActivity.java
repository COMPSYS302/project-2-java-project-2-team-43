package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String[] carCategories = new String[] {"Sedan", "Hatchback", "Convertible", "Coupe", "SUV", "Pickup"};
    private ArrayAdapter<String> adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, carCategories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = carCategories[position];
            Intent intent = new Intent(MainActivity.this, CarListActivity.class);
            intent.putExtra("SELECTED_CATEGORY", selectedCategory);
            startActivity(intent);
        });
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
package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ArrayList<Car> cars;
    private ArrayAdapter<Car> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        cars = getAllCars();

        ListView listView = findViewById(R.id.list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cars);
        listView.setAdapter(adapter);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(false);


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Car selectedCar = cars.get(i);
            Intent intent = new Intent(SearchActivity.this, CarDetailActivity.class);
            intent.putExtra("SelectedCar", selectedCar);
            startActivity(intent);
        });
    }

    private ArrayList<Car> getAllCars() {
        String json = loadJSONFromAsset();
        ArrayList<Car> carList = new ArrayList<>();
        try {
            JSONObject jsonObj = new JSONObject(json);
            JSONArray jsonArray = jsonObj.getJSONArray("cars");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject carObj = jsonArray.getJSONObject(i);
                String name = carObj.getString("name");
                String price = carObj.getString("price");
                String category = carObj.getString("category");
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
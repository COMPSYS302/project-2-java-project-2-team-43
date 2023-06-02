package com.example.carapp;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Car {
    private String name;
    private String price;
    private String category;
    private String description;
    private String image;
    private List<String> slideImages;

    public Car(String name, String price, String category, String description, String image, List<String> slideImages) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.slideImages = slideImages;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public List<String> getSlideImages() {
        return slideImages;
    }

    public static ArrayList<Car> getCars(android.content.Context context, String category) {
        ArrayList<Car> cars = new ArrayList<>();
        Gson gson = new Gson();
        try {
            InputStream is = context.getAssets().open("cars.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            Type carListType = new TypeToken<ArrayList<Car>>(){}.getType();
            ArrayList<Car> allCars = gson.fromJson(json, carListType);
            for (Car car: allCars) {
                if (car.getCategory().equals(category)) {
                    cars.add(car);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return cars;
    }
}
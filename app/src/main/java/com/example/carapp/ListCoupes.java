package com.example.carapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ListCoupes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_coupes);
    }

    public void showDetailsActivity(View v)
    {
        Intent detailsActivity = new Intent(this, DetailsActivity.class);
        startActivity(detailsActivity);
    }
}
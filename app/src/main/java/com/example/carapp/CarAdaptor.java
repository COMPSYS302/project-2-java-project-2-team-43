package com.example.carapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CarAdaptor extends ArrayAdapter {

    int mLayoutID;
    ArrayList<Car> mCars;
    Context mContext;

    class ViewHolder
    {
        final ImageView ImageView;
        TextView nameTextView, priceTextView;

        public ViewHolder(View currentListViewItem)
        {
            ImageView = currentListViewItem.findViewById(R.id.car_image);
            nameTextView = (TextView) currentListViewItem.findViewById(R.id.car_name);
            priceTextView = (TextView) currentListViewItem.findViewById(R.id.car_price);
        }
    }

    public CarAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<Car> objects) {
        super(context, resource, objects);
        mLayoutID=resource;
        mCars = objects;
        mContext=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Get a reference to the current ListView item
        View currentListViewItem = convertView;

        // Check if the existing view is being reused, otherwise inflate the view
        if (currentListViewItem == null) {
            currentListViewItem = LayoutInflater.from(getContext()).inflate(mLayoutID, parent, false);
        }

        ViewHolder vh = new ViewHolder(currentListViewItem);
        //Get the Car object for the current position
        Car currentCar = mCars.get(position);

        //Set the attributed of list_view_number_item views
        int i = mContext.getResources().getIdentifier(
                currentCar.getImage(), "drawable",
                mContext.getPackageName());

        //Setting the icon
        vh.ImageView.setImageResource(i);

        vh.nameTextView.setText(currentCar.getName());

        vh.priceTextView.setText(currentCar.getPrice());

        return currentListViewItem;
    }
}

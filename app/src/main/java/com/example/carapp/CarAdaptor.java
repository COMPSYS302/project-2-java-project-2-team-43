package com.example.carapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.carapp.Car;

import java.util.ArrayList;

public class CarAdaptor extends ArrayAdapter<Car> {

    int mLayoutID;
    ArrayList<Car> originalList;
    ArrayList<Car> filteredList;
    Context mContext;

    class ViewHolder
    {
        final ImageView ImageView;
        TextView nameTextView, priceTextView;

        public ViewHolder(View currentListViewItem)
        {
            ImageView = currentListViewItem.findViewById(R.id.car_image);
            nameTextView = currentListViewItem.findViewById(R.id.car_name);
            priceTextView = currentListViewItem.findViewById(R.id.car_price);
        }
    }

    public CarAdaptor(@NonNull Context context, int resource, @NonNull ArrayList<Car> objects) {
        super(context, resource, objects);
        mLayoutID = resource;
        mContext = context;
        this.originalList = new ArrayList<>(objects);
        this.filteredList = new ArrayList<>(objects);
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
        Car currentCar = getItem(position);

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

    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                ArrayList<Car> tempList = new ArrayList<>();
                // If constraint is null or has no characters, return the original list
                if (constraint == null || constraint.length() == 0) {
                    tempList.addAll(originalList);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    // Filter the original list based on the constraint
                    for (Car car : originalList) {
                        if (car.getName().toLowerCase().contains(filterPattern)) {
                            tempList.add(car);
                        }
                    }
                }
                filterResults.values = tempList;
                filterResults.count = tempList.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((ArrayList) results.values);
                notifyDataSetChanged();
            }
        };
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Car getItem(int position) {
        return filteredList.get(position);
    }
}

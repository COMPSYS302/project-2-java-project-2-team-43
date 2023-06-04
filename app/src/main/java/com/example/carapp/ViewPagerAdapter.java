package com.example.carapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Car> cars;

    public ViewPagerAdapter(Context context, List<Car> cars) {
        this.context = context;
        this.cars = cars;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return cars.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View view = layoutInflater.inflate(R.layout.pager_item, container, false);

        ImageView imageView = view.findViewById(R.id.image);

        int imageResource = context.getResources().getIdentifier(cars.get(position).getImage(), "drawable", context.getPackageName());
        imageView.setImageResource(imageResource);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CarDetailActivity.class);
                intent.putExtra("SelectedCar", cars.get(position));
                context.startActivity(intent);
            }
        });

        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

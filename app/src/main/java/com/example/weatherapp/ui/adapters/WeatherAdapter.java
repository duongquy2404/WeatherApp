package com.example.weatherapp.ui.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.ui.fragments.WeatherFragment;

import java.util.ArrayList;

public class WeatherAdapter extends FragmentStateAdapter {
    private ArrayList<City> cityArrayList;

    public WeatherAdapter(@NonNull FragmentActivity fragmentActivity, ArrayList<City> cityArrayList) {
        super(fragmentActivity);
        this.cityArrayList=cityArrayList;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return WeatherFragment.newInstance(cityArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return cityArrayList.size();
    }
}

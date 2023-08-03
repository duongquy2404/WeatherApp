package com.example.weatherapp.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.cityinfo.City;

public class WeatherFragment extends Fragment {
    private static final String ARG_LOCATION = "arg_location";

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance(City city) {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCATION, city);
        weatherFragment.setArguments(args);
        return weatherFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_weather, container, false);

        // Lấy đối tượng Location từ arguments
        City city = (City) getArguments().getSerializable(ARG_LOCATION);

        // Hiển thị thông tin thời tiết cho địa điểm tương ứng
        TextView cityTextView = rootView.findViewById(R.id.tv_location_main);
        TextView tv_time=rootView.findViewById(R.id.tv_time);
        cityTextView.setText(city.getKey()+"ahahah");

        // ... Thêm mã xử lý hiển thị thông tin thời tiết ở đây ...

        return rootView;
    }
}
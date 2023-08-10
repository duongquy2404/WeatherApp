package com.example.weatherapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.hourlyweather.HourlyWeather;
import com.example.weatherapp.utils.DataConverter;

import java.util.ArrayList;

public class HourlyAdapter extends RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder> {
    ArrayList<HourlyWeather> hourlyWeatherArrayList=new ArrayList<>();

    public HourlyAdapter(ArrayList<HourlyWeather> hourlyWeatherArrayList) {
        this.hourlyWeatherArrayList = hourlyWeatherArrayList;
    }

    @NonNull
    @Override
    public HourlyAdapter.HourlyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_item, parent, false);
        return new HourlyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapter.HourlyViewHolder holder, int position) {
        HourlyWeather hourlyWeather=hourlyWeatherArrayList.get(position);
        holder.bind(hourlyWeather);
    }

    @Override
    public int getItemCount() {
        return hourlyWeatherArrayList != null ? hourlyWeatherArrayList.size() : 0;
    }

    public class HourlyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_hourly_time,tv_hourly_temperature;
        private ImageView iv_hourly_icon;
        public HourlyViewHolder(View view) {
            super(view);
            tv_hourly_time= view.findViewById(R.id.tv_hourly_time);
            tv_hourly_temperature= view.findViewById(R.id.tv_hourly_temperature);
        }
        public void bind(HourlyWeather hourlyWeather ){
            tv_hourly_time.setText(DataConverter.convertTime(hourlyWeather.getDateTime()));
            tv_hourly_temperature.setText(Math.round(hourlyWeather.getTemperature().getValue())+"°");
        }
    }
}
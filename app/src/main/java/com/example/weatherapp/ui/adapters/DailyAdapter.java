package com.example.weatherapp.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.dailyweather.DailyForecast;
import com.example.weatherapp.model.hourlyweather.HourlyWeather;
import com.example.weatherapp.utils.DataConverter;

import java.util.ArrayList;

public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.DailyViewHolder> {
    ArrayList<DailyForecast> dailyForecastArrayList=new ArrayList<>();

    public DailyAdapter(ArrayList<DailyForecast> dailyForecastArrayList) {
        this.dailyForecastArrayList = dailyForecastArrayList;
    }

    @NonNull
    @Override
    public DailyAdapter.DailyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_item, parent, false);
        return new DailyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyAdapter.DailyViewHolder holder, int position) {
        DailyForecast dailyForecast= dailyForecastArrayList.get(position);
        holder.bind(dailyForecast);
    }

    @Override
    public int getItemCount() {
        return dailyForecastArrayList != null ? dailyForecastArrayList.size() : 0;
    }

    public class DailyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_daily_day,tv_daily_temperature_day,tv_daily_temperature_night;
        public DailyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_daily_day= itemView.findViewById(R.id.tv_daily_day);
            tv_daily_temperature_day=itemView.findViewById(R.id.tv_daily_temperature_day);
            tv_daily_temperature_night=itemView.findViewById(R.id.tv_daily_temperature_night);
        }

        @SuppressLint("SetTextI18n")
        public void bind(DailyForecast dailyForecast){
            tv_daily_day.setText(DataConverter.convertDate(dailyForecast.getDate()));
            tv_daily_temperature_day.setText(Math.round(dailyForecast.getTemperature().getMaximum().getValue())+"°");
            tv_daily_temperature_night.setText(Math.round(dailyForecast.getTemperature().getMinimum().getValue())+"°");
        }
    }
}

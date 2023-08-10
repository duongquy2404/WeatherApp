package com.example.weatherapp.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.model.currentweather.Current;
import com.example.weatherapp.model.dailyweather.DailyWeather;
import com.example.weatherapp.model.hourlyweather.HourlyWeather;
import com.example.weatherapp.repositories.WeatherRepository;
import com.example.weatherapp.utils.DataConverter;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    ArrayList<City> cityArrayList;
    public WeatherRepository weatherRepository;

    public LocationAdapter(ArrayList<City> cityArrayList) {
        this.cityArrayList = cityArrayList;
        weatherRepository=new WeatherRepository();
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {
        City city = cityArrayList.get(position);
        holder.bind(city);
    }

    @Override
    public int getItemCount() {
        return cityArrayList != null ? cityArrayList.size() : 0;
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_location2,tv_country,tv_datetime,tv_temp,tv_temp_max_min;
        private ImageView iv_iconweather;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_location2=itemView.findViewById(R.id.tv_location2);
            tv_country=itemView.findViewById(R.id.tv_country);
            tv_datetime=itemView.findViewById(R.id.tv_datetime);
            tv_temp=itemView.findViewById(R.id.tv_temp);
            tv_temp_max_min=itemView.findViewById(R.id.tv_temp_max_min);
            iv_iconweather=itemView.findViewById(R.id.iv_iconweather);
        }

        @SuppressLint("SetTextI18n")
        public void bind(City city) {
            tv_location2.setText(city.getLocalizedName());
            tv_country.setText(city.getAdministrativeArea().getLocalizedName()+
                    ", "+ city.getCountry().getLocalizedName());
            weatherRepository.getCurrentWeather(city.getKey(), "vi", new WeatherRepository.WeatherCallback() {
                @Override
                public void onSuccess(Current currentWeather) {
                    tv_datetime.setText(DataConverter.convert(currentWeather.getLocalObservationDateTime()));
                    tv_temp.setText(Math.round(currentWeather.getTemperature().getMetric().getValue())+"°");
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });

            weatherRepository.getDailyWeather(city.getKey(), new WeatherRepository.DailyCallback() {
                @Override
                public void onSuccess(DailyWeather dailyWeather) {
                    long temp_max=Math.round(dailyWeather.getDailyForecasts().get(0).getTemperature().getMaximum().getValue());
                    long temp_min= Math.round(dailyWeather.getDailyForecasts().get(0).getTemperature().getMinimum().getValue());
                    tv_temp_max_min.setText(temp_max+"°/"+temp_min+"°");
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        }
    }
}

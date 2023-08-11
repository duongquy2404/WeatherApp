package com.example.weatherapp.ui.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.model.currentweather.Current;
import com.example.weatherapp.model.dailyweather.DailyForecast;
import com.example.weatherapp.model.dailyweather.DailyWeather;
import com.example.weatherapp.model.hourlyweather.HourlyWeather;
import com.example.weatherapp.repositories.WeatherRepository;
import com.example.weatherapp.ui.adapters.DailyAdapter;
import com.example.weatherapp.ui.adapters.HourlyAdapter;
import com.example.weatherapp.utils.DataConverter;

import java.util.ArrayList;

public class WeatherFragment extends Fragment {
    private static final String ARG_LOCATION = "arg_location";

    private WeatherRepository weatherRepository=new WeatherRepository();
    private ArrayList<HourlyWeather> hourlyWeatherList = new ArrayList<>();
    private DailyWeather dailyWeather=new DailyWeather();
    private ArrayList<DailyForecast> dailyForecastArrayList=new ArrayList<>();

    public WeatherFragment() {

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
        TextView tv_location_main = rootView.findViewById(R.id.tv_location_main);
        TextView tv_time=rootView.findViewById(R.id.tv_time);
        TextView tv_temp=rootView.findViewById(R.id.tv_temp);
        TextView tv_weathertext=rootView.findViewById(R.id.tv_weathertext);
        ImageView iv_iconweather=rootView.findViewById(R.id.iv_iconweather);
        tv_location_main.setText(city.getLocalizedName());

        // Thiết lập RecycleView cho hourly
        RecyclerView rcv_hourly = rootView.findViewById(R.id.rcv_hourly);
        if (savedInstanceState != null) {
            hourlyWeatherList = (ArrayList<HourlyWeather>) savedInstanceState.getSerializable("hourlyWeatherList");
        }
        HourlyAdapter hourlyAdapter= new HourlyAdapter(hourlyWeatherList);
        rcv_hourly.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), LinearLayoutManager.HORIZONTAL);
        rcv_hourly.addItemDecoration(dividerItemDecoration);
        rcv_hourly.setAdapter(hourlyAdapter);

        // Thiết lập RecycleView cho daily
        RecyclerView rcv_daily= rootView.findViewById(R.id.rcv_daily);
        if(savedInstanceState != null){
            dailyForecastArrayList= (ArrayList<DailyForecast>) savedInstanceState.getSerializable("dailyForecastList");
        }
        DailyAdapter dailyAdapter=new DailyAdapter(dailyForecastArrayList);
        rcv_daily.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false));
        DividerItemDecoration dividerItemDecoration1=new DividerItemDecoration(getActivity(),LinearLayoutManager.HORIZONTAL);
        rcv_daily.addItemDecoration(dividerItemDecoration1);
        rcv_daily.setAdapter(dailyAdapter);


        // Call api thời tiết hiện tại
        weatherRepository.getCurrentWeather(city.getKey(), "vi", new WeatherRepository.WeatherCallback() {
            @Override
            public void onSuccess(Current currentWeather) {
                tv_time.setText(DataConverter.convert(currentWeather.getLocalObservationDateTime()));
                tv_temp.setText(Math.round(currentWeather.getTemperature().getMetric().getValue()) + "°");
                tv_weathertext.setText(currentWeather.getWeatherText());
            }
            @Override
            public void onFailure(Throwable throwable) {

            }
        });

        weatherRepository.getHourlyWeather(city.getKey(), new WeatherRepository.HourlyCallback() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(ArrayList<HourlyWeather> hourlyWeatherArrayList) {
                hourlyWeatherList.clear();
                hourlyWeatherList.add(hourlyWeatherArrayList.get(0));
                hourlyWeatherList.add(hourlyWeatherArrayList.get(2));
                hourlyWeatherList.add(hourlyWeatherArrayList.get(4));
                hourlyWeatherList.add(hourlyWeatherArrayList.get(6));
                hourlyWeatherList.add(hourlyWeatherArrayList.get(8));
                hourlyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });

        weatherRepository.getDailyWeather(city.getKey(), new WeatherRepository.DailyCallback() {
            @Override
            public void onSuccess(DailyWeather dailyWeather) {
                dailyForecastArrayList.clear();
                dailyForecastArrayList.addAll(dailyWeather.getDailyForecasts());
                dailyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
        return rootView;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("hourlyWeatherList", hourlyWeatherList);
        outState.putSerializable("dailyForecastList", dailyForecastArrayList);
    }
}
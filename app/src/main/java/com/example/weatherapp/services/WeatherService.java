package com.example.weatherapp.services;

import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.model.currentweather.Current;
import com.example.weatherapp.model.dailyweather.DailyForecast;
import com.example.weatherapp.model.dailyweather.DailyWeather;
import com.example.weatherapp.model.hourlyweather.HourlyWeather;
import com.example.weatherapp.repositories.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {
    // Tìm kiếm thành phố dựa vào từ khóa
    @GET("locations/v1/cities/autocomplete")
    Call<ArrayList<City>> getAutoCompleteCities(
            @Query("apikey") String apiKey,
            @Query("q") String query,
            @Query("language") String language
    );

    // Lấy thông tin thành phố dựa trên locationKey
    @GET("locations/v1/{locationKey}")
    Call<City> getLocationByKey(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey,
            @Query("language") String language
    );

    // Lấy thông tin thời tiết hiện tại dựa trên locationKey
    @GET("currentconditions/v1/{locationKey}")
    Call<ArrayList<Current>> getCurrentConditions(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey,
            @Query("language") String language
    );

    // Lấy thông tin thời tiết của 12 khung giờ tiếp theo
    @GET("forecasts/v1/hourly/12hour/{locationKey}")
    Call<ArrayList<HourlyWeather>> getHourlyWeather(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey,
            @Query("language") String language,
            @Query("metric") boolean metric
    );

    // Lấy thông tin thời tiết của 5 ngày(bao gồm ngày hiện tại)
    @GET("forecasts/v1/daily/5day/{locationKey}")
    Call<DailyWeather> getDailyWeather(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey,
            @Query("language") String language,
            @Query("metric") boolean metric
    );
}

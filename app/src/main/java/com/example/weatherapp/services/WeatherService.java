package com.example.weatherapp.services;

import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.model.currentweather.Current;
import com.example.weatherapp.model.dailyweather.DailyForecast;
import com.example.weatherapp.model.hourlyweather.HourlyWeather;
import com.example.weatherapp.repositories.WeatherRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("locations/v1/cities/autocomplete")
    Call<ArrayList<City>> getAutoCompleteCities(
            @Query("apikey") String apiKey,
            @Query("q") String query,
            @Query("language") String language
    );
    @GET("locations/v1/cities/search")
    Call<ArrayList<City>> getLocation(
            @Query("apikey") String apiKey,
            @Query("q") String query
    );

    @GET("currentconditions/v1/{locationKey}")
    Call<List<Current>> getCurrentConditions(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey
    );

    @GET("forecasts/v1/hourly/12hour/{locationKey}")
    Call<List<HourlyWeather>> getHourlyWeather(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey
    );

    @GET("forecasts/v1/daily/5day/{locationKey}")
    Call<List<DailyForecast>> getFiveDayForecast(
            @Path("locationKey") String locationKey,
            @Query("apikey") String apiKey
    );
}

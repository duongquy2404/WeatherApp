package com.example.weatherapp.repositories;

import static com.example.weatherapp.utils.Constants.BASE_URL;

import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.model.currentweather.Current;
import com.example.weatherapp.services.WeatherService;
import com.example.weatherapp.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherRepository {
    private WeatherService weatherService;

    public WeatherRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        weatherService = retrofit.create(WeatherService.class);
    }

    public void searchCities(String query, String language, final CitySearchCallback callback){
        Call<ArrayList<City>> call = weatherService.getAutoCompleteCities(Constants.API_KEY, query, language);
        call.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                if (response.isSuccessful()) {
                    ArrayList<City> cities = response.body();
                    callback.onSuccess(cities);
                } else {
                    // Xử lí khi gặp lỗi
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                // Xử lý khi gọi API thất bại
                callback.onFailure(t);
            }
        });
    }

    public interface CitySearchCallback {
        void onSuccess(ArrayList<City> cities);

        void onFailure(Throwable throwable);
    }

    public void getCities(String query, final CitySearchCallback1 callback) {
        Call<ArrayList<City>> call = weatherService.getLocation(Constants.API_KEY,query);
        call.enqueue(new Callback<ArrayList<City>>() {
            @Override
            public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
                if (response.isSuccessful()) {
                    List<City> cities = response.body();
                    callback.onSuccess(cities);
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface CitySearchCallback1 {
        void onSuccess(List<City> cities);

        void onFailure(Throwable throwable);
    }

    public void getCurrentWeather(String locationKey, final WeatherCallback callback) {
        Call<List<Current>> call = weatherService.getCurrentConditions(locationKey, Constants.API_KEY);
        call.enqueue(new Callback<List<Current>>() {
            @Override
            public void onResponse(Call<List<Current>> call, Response<List<Current>> response) {
                if (response.isSuccessful()) {
                    List<Current> currentWeatherList = response.body();
                    if (currentWeatherList != null && !currentWeatherList.isEmpty()) {
                        Current currentWeather = currentWeatherList.get(0);
                        callback.onSuccess(currentWeather);
                    } else {
                        callback.onFailure(new Throwable("No weather data available"));
                    }
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<Current>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface WeatherCallback {
        void onSuccess(Current currentWeather);

        void onFailure(Throwable throwable);
    }
}

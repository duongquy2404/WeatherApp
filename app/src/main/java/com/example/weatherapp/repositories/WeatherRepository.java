package com.example.weatherapp.repositories;

import static com.example.weatherapp.utils.Constants.BASE_URL;

import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.model.currentweather.Current;
import com.example.weatherapp.model.dailyweather.DailyWeather;
import com.example.weatherapp.model.hourlyweather.HourlyWeather;
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

    public void getCityByKey(String locationKey, String language, final CityCallBack callback){
        Call<City> call=weatherService.getLocationByKey(locationKey, Constants.API_KEY, language);
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.isSuccessful()) {
                    City city = response.body();
                    callback.onSuccess(city);
                } else {
                    // Xử lí khi gặp lỗi
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                // Xử lý khi gọi API thất bại
                callback.onFailure(t);
            }
        });
    }

    public interface CityCallBack{
        void onSuccess(City city);
        void onFailure(Throwable throwable);
    }

    public void getCurrentWeather(String locationKey, String language, final WeatherCallback callback) {
        Call<ArrayList<Current>> call = weatherService.getCurrentConditions(locationKey, Constants.API_KEY, language);
        call.enqueue(new Callback<ArrayList<Current>>() {
            @Override
            public void onResponse(Call<ArrayList<Current>> call, Response<ArrayList<Current>> response) {
                if (response.isSuccessful()) {
                    ArrayList<Current> currentArrayList = response.body();
                    if (currentArrayList != null && !currentArrayList.isEmpty()) {
                        Current currentWeather = currentArrayList.get(0);
                        callback.onSuccess(currentWeather);
                    } else {
                        callback.onFailure(new Throwable("No weather data available"));
                    }
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Current>> call, Throwable t) {
                callback.onFailure(t);
            }
        });
    }

    public interface WeatherCallback {
        void onSuccess(Current currentWeather);

        void onFailure(Throwable throwable);
    }

    public void getHourlyWeather(String locationKey, final HourlyCallback callback){
        Call<ArrayList<HourlyWeather>> call = weatherService.getHourlyWeather(locationKey, Constants.API_KEY,"vi",true);
        call.enqueue(new Callback<ArrayList<HourlyWeather>>() {
            @Override
            public void onResponse(Call<ArrayList<HourlyWeather>> call, Response<ArrayList<HourlyWeather>> response) {
                if (response.isSuccessful()) {
                    ArrayList<HourlyWeather> hourlyWeatherArrayList = response.body();
                    if (hourlyWeatherArrayList != null && !hourlyWeatherArrayList.isEmpty()) {
                        callback.onSuccess(hourlyWeatherArrayList);
                    } else {
                        callback.onFailure(new Throwable("No weather data available"));
                    }
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<HourlyWeather>> call, Throwable t) {

            }
        });
    }

    public interface HourlyCallback {
        void onSuccess(ArrayList<HourlyWeather> hourlyWeatherArrayList);

        void onFailure(Throwable throwable);
    }

    public void getDailyWeather(String locationKey, final DailyCallback callback){
        Call<DailyWeather> call=weatherService.getDailyWeather(locationKey,Constants.API_KEY,"vi",true);
        call.enqueue(new Callback<DailyWeather>() {
            @Override
            public void onResponse(Call<DailyWeather> call, Response<DailyWeather> response) {
                if (response.isSuccessful()) {
                    DailyWeather dailyWeather= response.body();
                    if (dailyWeather!=null) {
                        callback.onSuccess(dailyWeather);
                    } else {
                        callback.onFailure(new Throwable("No weather data available"));
                    }
                } else {
                    callback.onFailure(new Throwable("Error: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<DailyWeather> call, Throwable t) {

            }
        });
    }

    public interface DailyCallback {
        void onSuccess(DailyWeather dailyWeather);

        void onFailure(Throwable throwable);
    }
}

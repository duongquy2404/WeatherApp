package com.example.weatherapp.ui.activities;

import static com.example.weatherapp.utils.Constants.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.model.currentweather.Current;
import com.example.weatherapp.model.currentweather.Temperature;
import com.example.weatherapp.repositories.WeatherRepository;
import com.example.weatherapp.ui.adapters.CityAdapter;
import com.example.weatherapp.ui.adapters.WeatherAdapter;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {
    private TextView tv_location1;
    private SearchView searchView;
    private ImageButton ib_menu;
    private CircleIndicator3 indicator;
    private ViewPager2 vp_weather;
    private TextView tv_location_main;
    private ArrayList<City> cityArrayList;
    private WeatherAdapter weatherAdapter;
    private WeatherRepository weatherRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickEvent();
        cityArrayList=new ArrayList<City>();
        City city=new City();
        city.setVersion(1);
        city.setKey("839313");
        city.setType("city1");
        City city2=new City();
        city2.setVersion(2);
        city2.setKey("53758");
        city2.setType("city2");
        cityArrayList.add(city);
        cityArrayList.add(city2);
        searchCities("hanoi");
        showCurrentWeather();
        setupViewPager();
        indicator.setViewPager(vp_weather);
    }

    public void init(){
        tv_location1=(TextView) findViewById(R.id.tv_location1);
        searchView=(SearchView) findViewById(R.id.searchView);
        ib_menu=(ImageButton) findViewById(R.id.ib_menu);
        indicator=(CircleIndicator3) findViewById(R.id.indicator);
        vp_weather=(ViewPager2) findViewById(R.id.vp_weather);

        tv_location_main=(TextView) findViewById(R.id.tv_location_main);
        weatherRepository=new WeatherRepository();
    }

    public void clickEvent(){
        tv_location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, LocationActivity.class);
                startActivity(intent);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //WeatherRepository.fetchDataForLocation(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    public void setupViewPager(){
        weatherAdapter=new WeatherAdapter(this, cityArrayList);
        weatherAdapter.notifyDataSetChanged();
        vp_weather.setAdapter(weatherAdapter);
    }

    public void showCurrentWeather(){
        String locationKey = "353412";
        weatherRepository.getCurrentWeather(locationKey, new WeatherRepository.WeatherCallback() {
            @Override
            public void onSuccess(Current currentWeather) {
                if (currentWeather != null) {
                    String weatherText = currentWeather.getWeatherText();
                    Temperature temperature = currentWeather.getTemperature();
                    //String temperatureUnit = currentWeather.getTemperatureUnit();

                    // Example: Display the weather information in a TextView
                    //TextView weatherInfoTextView = findViewById(R.id.weatherInfoTextView);
                    //weatherInfoTextView.setText("Weather: " + weatherText + "\nTemperature: " + temperature + " " + temperatureUnit);
                } else {
                    // Data is empty or invalid
                    Toast.makeText(MainActivity.this, "No weather data available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }

    public void searchCities(String query) {
        weatherRepository.getCities(query, new WeatherRepository.CitySearchCallback1() {
            @Override
            public void onSuccess(List<City> cities) {
                City city=cities.get(0);
                cityArrayList.add(city);
                weatherAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
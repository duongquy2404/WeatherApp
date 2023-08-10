package com.example.weatherapp.ui.activities;

import static com.example.weatherapp.utils.Constants.BASE_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.example.weatherapp.database.LocationDatabase;
import com.example.weatherapp.entity.Location;
import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.model.currentweather.Current;
import com.example.weatherapp.model.currentweather.Temperature;
import com.example.weatherapp.repositories.WeatherRepository;
import com.example.weatherapp.ui.adapters.CityAdapter;
import com.example.weatherapp.ui.adapters.WeatherAdapter;
import com.example.weatherapp.utils.DataLocalManager;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator3;

public class MainActivity extends AppCompatActivity {
    private TextView tv_location1, tv_location_main;
    private SearchView searchView;
    private ImageButton ib_menu;
    private CircleIndicator3 indicator;
    private ViewPager2 vp_weather;
    private ArrayList<City> cityArrayList;
    private WeatherAdapter weatherAdapter;
    private WeatherRepository weatherRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        clickEvent();
        setupViewPager();
        indicator.setViewPager(vp_weather);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setCityArrayList();
        setupViewPager();
    }

    public void init(){
        tv_location1=(TextView) findViewById(R.id.tv_location1);
        searchView=(SearchView) findViewById(R.id.searchView);
        ib_menu=(ImageButton) findViewById(R.id.ib_menu);
        indicator=(CircleIndicator3) findViewById(R.id.indicator);
        vp_weather=(ViewPager2) findViewById(R.id.vp_weather);
        cityArrayList=new ArrayList<>();
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

    public void setCityArrayList(){
        List<Location> locationArrayList=new ArrayList<>();
        locationArrayList= LocationDatabase.getInstance(this).locationDao().getListLocation();
        cityArrayList.clear();
        for(int i=0;i<locationArrayList.size();i++){
            Location location=locationArrayList.get(i);
            String key=location.getKey();
            weatherRepository.getCityByKey(key, "vi", new WeatherRepository.CityCallBack() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onSuccess(City city) {
                    cityArrayList.add(city);
                    weatherAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        }
    }
}
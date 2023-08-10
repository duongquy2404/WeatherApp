package com.example.weatherapp.ui.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.weatherapp.R;
import com.example.weatherapp.database.LocationDatabase;
import com.example.weatherapp.entity.Location;
import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.model.currentweather.Current;
import com.example.weatherapp.repositories.WeatherRepository;
import com.example.weatherapp.ui.adapters.CityAdapter;
import com.example.weatherapp.ui.adapters.LocationAdapter;
import com.example.weatherapp.utils.DataLocalManager;
import com.example.weatherapp.utils.SharedPreferencesHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class LocationActivity extends AppCompatActivity {
    private ImageView iv_back, iv_menu;
    private RecyclerView rcv_location;
    private FloatingActionButton fab;

    private ArrayList<City> cityArrayList;
    private LocationAdapter locationAdapter;
    private WeatherRepository weatherRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        init();
        setCityArrayList();
        clickEvent();
        setupRecycleView();
    }

    public void init(){
        iv_back=(ImageView) findViewById(R.id.iv_back);
        iv_menu=(ImageView) findViewById(R.id.iv_menu);
        rcv_location=(RecyclerView) findViewById(R.id.rcv_location);
        fab=(FloatingActionButton) findViewById(R.id.fab);
        cityArrayList=new ArrayList<>();
        locationAdapter=new LocationAdapter(cityArrayList);
        weatherRepository=new WeatherRepository();
    }

    public void clickEvent(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hiển thị menu
                showMenu(view);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSearchButtonClick(view);
            }
        });
    }

    public void setupRecycleView(){
        rcv_location.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        rcv_location.addItemDecoration(dividerItemDecoration);
        rcv_location.setAdapter(locationAdapter);
    }

    public void onSearchButtonClick(View view) {
        Intent intent = new Intent(LocationActivity.this, SearchActivity.class);
        startActivityForResult(intent, 1);
    }

    // Thêm location vào cityArraylist và lưu locationKey vào RoomDB
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                City city = (City) data.getSerializableExtra("SELECTED_OBJECT");
                if (city != null) {
                    cityArrayList.add(city);
                    Location location=new Location(city.getKey());
                    LocationDatabase.getInstance(this).locationDao().insertLocation(location);
                    locationAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    // Lấy ra thông tin vị trí, thời điểm, thời tiết dựa vào locationKey đc lưu trong RoomDB
    public void setCityArrayList(){
        List<Location> locationArrayList=new ArrayList<>();
        locationArrayList=LocationDatabase.getInstance(this).locationDao().getListLocation();
        for(int i=0;i<locationArrayList.size();i++){
            Location location=locationArrayList.get(i);
            String key=location.getKey();
            weatherRepository.getCityByKey(key, "vi", new WeatherRepository.CityCallBack() {
                @Override
                public void onSuccess(City city) {
                    cityArrayList.add(city);
                    locationAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(Throwable throwable) {

                }
            });
        }
    }

    public void showMenu(View view){
        PopupMenu popupMenu=new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_item,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                return false;
            }
        });
        popupMenu.show();
    }
}
package com.example.weatherapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.ui.adapters.CityAdapter;
import com.example.weatherapp.ui.adapters.LocationAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity {
    private ImageView iv_back, iv_menu;
    private RecyclerView rcv_location;
    private FloatingActionButton fab;

    private ArrayList<City> cityArrayList;
    private LocationAdapter locationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        init();
        clickEvent();
        setupRecycleView();
    }

    public void init(){
        iv_back=(ImageView) findViewById(R.id.iv_back);
        iv_menu=(ImageView) findViewById(R.id.iv_menu);
        rcv_location=(RecyclerView) findViewById(R.id.rcv_location);
        fab=(FloatingActionButton) findViewById(R.id.fab);
        cityArrayList=new ArrayList<>();

        City city1 = (City) getIntent().getSerializableExtra("CITY_LIST");
        if(city1!=null){
            cityArrayList.add(city1);
        }
        locationAdapter=new LocationAdapter(cityArrayList);
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
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LocationActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    public void setupRecycleView(){
        rcv_location.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        rcv_location.addItemDecoration(dividerItemDecoration);
        rcv_location.setAdapter(locationAdapter);
    }
}
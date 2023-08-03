package com.example.weatherapp.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.cityinfo.City;
import com.example.weatherapp.repositories.WeatherRepository;
import com.example.weatherapp.ui.adapters.CityAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private ImageView iv_back;
    private SearchView searchView1;

    private RecyclerView rcv_locationLoad;
    private ArrayList<City> cityArrayList;
    private CityAdapter cityAdapter;
    private WeatherRepository weatherRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        init();
        setupReclyceView();
        clickEvent();
    }

    public void init(){
        iv_back=(ImageView) findViewById(R.id.iv_back);
        searchView1=(SearchView) findViewById(R.id.searchView1);
        rcv_locationLoad=(RecyclerView) findViewById(R.id.rcv_locationLoad);
        cityArrayList=new ArrayList<City>();
        cityAdapter=new CityAdapter();
        weatherRepository=new WeatherRepository();
    }

    public void clickEvent(){
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        searchView1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchCities(query,"vi");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)) {
                    searchCities(newText,"vi");
                } else {
                    // Clear the list when search query is empty
                    cityAdapter.setData(new ArrayList<>());
                }
                return true;
            }
        });
    }

    public void setupReclyceView(){
        cityAdapter.notifyDataSetChanged();
        rcv_locationLoad.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        rcv_locationLoad.addItemDecoration(dividerItemDecoration);
        rcv_locationLoad.setAdapter(cityAdapter);
    }

    public void searchCities(String query, String language){
        weatherRepository.searchCities(query, language, new WeatherRepository.CitySearchCallback() {
            @Override
            public void onSuccess(ArrayList<City> cities) {
                cityAdapter.setData(cities);
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
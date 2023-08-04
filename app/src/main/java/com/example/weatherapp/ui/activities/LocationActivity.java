package com.example.weatherapp.ui.activities;

import androidx.annotation.Nullable;
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
        //addLocation();
    }

    public void init(){
        iv_back=(ImageView) findViewById(R.id.iv_back);
        iv_menu=(ImageView) findViewById(R.id.iv_menu);
        rcv_location=(RecyclerView) findViewById(R.id.rcv_location);
        fab=(FloatingActionButton) findViewById(R.id.fab);
        cityArrayList=new ArrayList<>();
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
//                Intent intent=new Intent(LocationActivity.this, SearchActivity.class);
//                startActivity(intent);
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

    public void addLocation(){
        Intent intent= getIntent();
        if(intent.hasExtra("CITY_LIST")){
            City city=(City) intent.getSerializableExtra("CITY_LIST");
            cityArrayList.add(city);
            locationAdapter.notifyDataSetChanged();

        }
    }

    public void onSearchButtonClick(View view) {
        Intent intent = new Intent(LocationActivity.this, SearchActivity.class);
        startActivityForResult(intent, 1); // 1 là requestCode
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Kiểm tra requestCode và resultCode
        if (requestCode == 1 && resultCode == RESULT_OK) {
            // Nhận dữ liệu từ Intent (nếu có)
            if (data != null) {
                // Lấy object được trả về từ SearchActivity (nếu bạn truyền object qua Intent)
                City city = (City) data.getSerializableExtra("SELECTED_OBJECT");

                // Thêm object vào ArrayList hoặc xử lý dữ liệu tương ứng
                if (city != null) {
                    // Thêm selectedObject vào ArrayList (nếu bạn muốn)
                    cityArrayList.add(city);

                    // Gọi notifyDataSetChanged() để cập nhật RecyclerView hoặc ListView
                    locationAdapter.notifyDataSetChanged();
                }

                // Xử lý dữ liệu tương ứng (nếu cần)
                // ...
            }
        }
    }
}
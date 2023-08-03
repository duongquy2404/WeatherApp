package com.example.weatherapp.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.R;
import com.example.weatherapp.model.cityinfo.City;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder> {
    ArrayList<City> cityArrayList;

    public LocationAdapter(ArrayList<City> cityArrayList) {
        this.cityArrayList = cityArrayList;
    }

    @NonNull
    @Override
    public LocationAdapter.LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.LocationViewHolder holder, int position) {
        City city = cityArrayList.get(position);
        holder.bind(city);
    }

    @Override
    public int getItemCount() {
        return cityArrayList != null ? cityArrayList.size() : 0;
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_location2;
        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_location2=itemView.findViewById(R.id.tv_location2);
        }

        public void bind(City city) {
            tv_location2.setText(city.getKey());
        }
    }
}

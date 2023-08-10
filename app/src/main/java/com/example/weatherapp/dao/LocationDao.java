package com.example.weatherapp.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.weatherapp.entity.Location;
import com.example.weatherapp.model.cityinfo.City;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface LocationDao {
    @Insert
    void insertLocation(Location location);

    @Query("SELECT * FROM location")
    List<Location> getListLocation();

    @Query("DELETE FROM location WHERE `key` = :itemKey")
    void deleteById(String itemKey);
}

package com.example.weatherapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.weatherapp.dao.LocationDao;
import com.example.weatherapp.entity.Location;
@Database(entities = {Location.class}, version = 1)
public abstract class LocationDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "location.db";
    private static LocationDatabase instance;

    // Tuần tự
    // Áp dụng pattern Singleton
    public static synchronized LocationDatabase getInstance(Context context){
        if(instance==null){
            instance= Room.databaseBuilder(context.getApplicationContext(), LocationDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries() //Cho phép query data trên mainthread
                    .build();
        }
        return instance;
    }

    public abstract LocationDao locationDao();
}
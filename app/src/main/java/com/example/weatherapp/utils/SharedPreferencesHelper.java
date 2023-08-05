package com.example.weatherapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.weatherapp.model.cityinfo.City;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPreferencesHelper {
    private static final String KEY_LIST_OBJECT = "list_object";
    private Context mContext;

    public SharedPreferencesHelper(Context mContext){
        this.mContext=mContext;
    }

    public void putStringValue(String key,String value){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(KEY_LIST_OBJECT,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getStringValue(String key){
        SharedPreferences sharedPreferences=mContext.getSharedPreferences(KEY_LIST_OBJECT,
                Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"");
    }
}

package com.example.weatherapp.utils;

import android.content.Context;

import com.example.weatherapp.model.cityinfo.City;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataLocalManager {
    private static final String PREF_LIST_CITY="PREF_LIST_CITY";
    private static DataLocalManager instance;
    private SharedPreferencesHelper sharedPreferencesHelper;

    public static void init(Context context){
        instance=new DataLocalManager();
        instance.sharedPreferencesHelper=new SharedPreferencesHelper(context);
    }

    public static DataLocalManager getInstance(){
        if(instance ==null){
            instance=new DataLocalManager();
        }
        return instance;
    }

    public static void setListCitys(ArrayList<City> cityArrayList){
        Gson gson=new Gson();
        JsonArray jsonArray=gson.toJsonTree(cityArrayList).getAsJsonArray();
        String strJsonArray=jsonArray.toString();
        DataLocalManager.getInstance().sharedPreferencesHelper.putStringValue(PREF_LIST_CITY,strJsonArray);
    }

    public static ArrayList<City> getListCitys(){
        String strJsonArray=DataLocalManager.getInstance().sharedPreferencesHelper.getStringValue(PREF_LIST_CITY);
        ArrayList<City> cityArrayList=new ArrayList<>();
        try {
            JSONArray jsonArray=new JSONArray(strJsonArray);
            JSONObject jsonObject;
            City city;
            Gson gson=new Gson();
            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);
                city=gson.fromJson(jsonObject.toString(),City.class);
                cityArrayList.add(city);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return cityArrayList;
    }
}

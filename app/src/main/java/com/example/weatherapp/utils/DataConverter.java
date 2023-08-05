package com.example.weatherapp.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DataConverter {
    public static String convertDateTime(String inputTimeStr, String timeZone){
        Instant instant = Instant.parse(inputTimeStr);

        LocalDateTime localDateTime = instant.atZone(ZoneId.of(timeZone)).toLocalDateTime();

        String dayOfWeek = "T." + localDateTime.getDayOfWeek().getValue();
        String day = String.valueOf(localDateTime.getDayOfMonth());
        String month = String.valueOf(localDateTime.getMonthValue());
        //String time = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        String[] monthNames = {
                "", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8",
                "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        };
        String monthName = monthNames[Integer.parseInt(month)];

        String resultStr = dayOfWeek + ", " + day + " " + monthName;
        return resultStr;
    }
}

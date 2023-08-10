package com.example.weatherapp.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataConverter {
    public static String convertDateTime(String inputTimeStr, String timeZone){
        Instant instant = Instant.parse(inputTimeStr);

        LocalDateTime localDateTime = instant.atZone(ZoneId.of(timeZone)).toLocalDateTime();

        String dayOfWeek = "T." + localDateTime.getDayOfWeek().getValue();
        String day = String.valueOf(localDateTime.getDayOfMonth());
        String month = String.valueOf(localDateTime.getMonthValue());
        String time = localDateTime.format(DateTimeFormatter.ofPattern("HH:mm"));

        String[] monthNames = {
                "", "Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4",
                "Tháng 5", "Tháng 6", "Tháng 7", "Tháng 8",
                "Tháng 9", "Tháng 10", "Tháng 11", "Tháng 12"
        };
        String monthName = monthNames[Integer.parseInt(month)];

        String resultStr = dayOfWeek + ", " + day + " " + monthName;
        return resultStr;
    }

    public static String convertTime(String inputTimeStr){
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(inputTimeStr);
        int hour = offsetDateTime.getHour();
        int minute = offsetDateTime.getMinute();

        return hour+":"+minute+"0";
    }

    public static String convert(String inputDateTime){
        OffsetDateTime offsetDateTime = OffsetDateTime.parse(inputDateTime);
        int dayOfWeek = offsetDateTime.getDayOfWeek().getValue();
        int dayOfMonth = offsetDateTime.getDayOfMonth();
        int month = offsetDateTime.getMonthValue();

        String dayOfWeekText = getDayOfWeekText(dayOfWeek);
        String monthText = getMonthText(month, offsetDateTime.getOffset());

        String result = dayOfWeekText + ", " + dayOfMonth + " " + monthText;
        return result;
    }

    private static String getDayOfWeekText(int dayOfWeek) {
        String[] daysOfWeek = {"", "T.2", "T.3", "T.4", "T.5", "T.6", "T.7", "CN"};
        return daysOfWeek[dayOfWeek];
    }

    private static String getMonthText(int month, ZoneOffset offset) {
        return OffsetDateTime.of(2023, month, 1, 0, 0, 0, 0, offset)
                .format(DateTimeFormatter.ofPattern("MMMM", new Locale("vi")));
    }
}

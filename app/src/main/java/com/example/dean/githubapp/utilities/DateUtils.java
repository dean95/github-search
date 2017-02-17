package com.example.dean.githubapp.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    private DateUtils() {

    }

    public static String formatDate(String data) {
        String date = getDate(data);
        DateFormat input = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date dateObj = null;
        try {
            dateObj = input.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat output = new SimpleDateFormat("LLL dd, yyyy", Locale.ENGLISH);

        return output.format(dateObj);
    }

    public static String formatTime(String data) {
        String time = getTime(data);
        DateFormat input = new SimpleDateFormat("hh:MM:ss", Locale.ENGLISH);
        Date dateObj = null;
        try {
            dateObj = input.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat output = new SimpleDateFormat("h:mm a", Locale.ENGLISH);
        return output.format(dateObj);
    }

    public static String getDate(String data) {
        String date = null;
        if(data.contains("T") && data.contains("Z")) {
            String[] parts = data.split("T|Z");
            date = parts[0];
        }
        return date;
    }

    public static String getTime(String data) {
        String time = null;
        if(data.contains("T") && data.contains("Z")) {
            String[] parts = data.split("T|Z");
            time = parts[1];
        }
        return time;
    }
}

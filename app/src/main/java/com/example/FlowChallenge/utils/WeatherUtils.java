package com.example.FlowChallenge.utils;

import com.example.FlowChallenge.R;
import com.example.FlowChallenge.model.WeatherResult;

import java.util.Calendar;

public class WeatherUtils {

    public static String getTempFormat(String stringTemp) {
        double temp = Double.parseDouble(stringTemp);
        return Math.round(temp) + "°C";
    }

    public static String getDay(int adapterPosition) {
        Calendar calendario = Calendar.getInstance();
        String day = "";
        int num = calendario.get(Calendar.DAY_OF_WEEK) + adapterPosition + 1;
        if (adapterPosition == 0) {
            day = "Mañana";
        } else {
            if (num == 1 || num == 8) day = "Domingo";
            if (num == 2 || num == 9) day = "Lunes";
            if (num == 3 || num == 10) day = "Martes";
            if (num == 4 || num == 11) day = "Miercoles";
            if (num == 5) day = "Jueves";
            if (num == 6) day = "Viernes";
            if (num == 7) day = "Sabado";
        }

        return day;
    }

    public static WeatherResult getLatAndLon(WeatherResult data) {

        switch (data.getCityName()) {
            case "New York":
                data.setLat("40.6643");
                data.setLon("-73.9385");
                break;
            case "Paris":
                data.setLat("48.8032");
                data.setLon("2.3511");
                break;
            case "Rio de Janeiro":
                data.setLat("-22.9035");
                data.setLon("-43.2096");
                break;
            case "Berlin":
                data.setLat("52.52437");
                data.setLon("13.41053");
                break;
            case "Madrid":
                data.setLat("40.4167");
                data.setLon("-3.70325");
                break;

        }

        return data;
    }

    public static int getIcon(String city) {
        switch (city) {
            case "New York":
                return (R.drawable.ic_ny);
            case "Paris":
                return (R.drawable.ic_paris);
            case "Rio de Janeiro":
                return (R.drawable.ic_rio);
            case "Berlin":
                return (R.drawable.ic_berlin);
            case "Madrid":
                return (R.drawable.ic_madrid);
            case "Ciudad Actual":
                return (R.drawable.ic_city);
            default:
                return R.drawable.ic_city;
        }

    }


}

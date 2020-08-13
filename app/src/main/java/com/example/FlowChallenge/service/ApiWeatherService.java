package com.example.FlowChallenge.service;



import com.example.FlowChallenge.model.WeatherForecastResult;
import com.example.FlowChallenge.model.WeatherTodayResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWeatherService {


    @GET("data/2.5/weather?&lang=es")
    Single<WeatherTodayResult> getTodayResult(@Query("q") String id, @Query("units") String metric, @Query("appid") String appid);

    @GET("data/2.5/forecast?&lang=es")
    Single<WeatherForecastResult> getForecastResult(@Query("q") String id, @Query("units") String metric, @Query("appid") String appid);

}

// https://api.openweathermap.org/data/2.5/forecast?q=Buenos%20Aires&appid=79e508708d93759b5d5adbabca775142}
//https://api.openweathermap.org/data/2.5/forecast?q=Buenos%20Aires&appid=79e508708d93759b5d5adbabca775142&lang=es

//https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={your api key}



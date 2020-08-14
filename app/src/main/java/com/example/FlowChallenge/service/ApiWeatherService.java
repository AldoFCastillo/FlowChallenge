package com.example.FlowChallenge.service;
import com.example.FlowChallenge.model.WeatherResult;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiWeatherService {

    @GET("data/2.5/onecall?&lang=es")
    Single<WeatherResult> getWeatherResult(@Query("lat") String lat,
                                           @Query("lon") String lon,
                                           @Query("units") String metric,
                                           @Query("appid") String appid);

}



//https://api.openweathermap.org/data/2.5/onecall?&lang=es&lat=33.441792&lon=-94.037689&exclude=hourly,minutely&appid=79e508708d93759b5d5adbabca775142





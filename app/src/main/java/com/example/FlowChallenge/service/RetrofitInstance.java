package com.example.FlowChallenge.service;

import com.example.FlowChallenge.model.IpAddress;
import com.example.FlowChallenge.model.IpapiResult;
import com.example.FlowChallenge.model.WeatherForecastResult;
import com.example.FlowChallenge.model.WeatherTodayResult;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL_WEATHER = "https://api.openweathermap.org/";
    private static final String BASE_URL_IPAPI = "http://api.ipapi.com/";
    public static final String BASE_URL_IP = "https://api.ipify.org/";
    private static RetrofitInstance retrofitInstanceWeather;
    private static RetrofitInstance retrofitInstanceIpapi;
    private static RetrofitInstance retrofitInstanceIp;
    private Retrofit retrofit;


    private RetrofitInstance(String url) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }


    public static synchronized RetrofitInstance getInstanceWeather() {
        if (retrofitInstanceWeather == null) {
            retrofitInstanceWeather = new RetrofitInstance(BASE_URL_WEATHER);
        }
        return retrofitInstanceWeather;
    }

    public static synchronized RetrofitInstance getInstanceIpapi() {
        if (retrofitInstanceIpapi == null) {
            retrofitInstanceIpapi = new RetrofitInstance(BASE_URL_IPAPI);
        }
        return retrofitInstanceIpapi;
    }

    public static synchronized RetrofitInstance getInstanceIP() {
        if (retrofitInstanceIp == null) {
            retrofitInstanceIp = new RetrofitInstance(BASE_URL_IP);
        }
        return retrofitInstanceIp;
    }


    public Single<WeatherTodayResult> getApiWeatherTodayService(String city, String unit, String key) {
        return retrofit.create(ApiWeatherService.class).getTodayResult(city, unit, key);
    }

    public Single<WeatherForecastResult> getApiWeatherForecastService(String city, String unit, String key) {
        return retrofit.create(ApiWeatherService.class).getForecastResult(city, unit, key);
    }

    public Single<IpapiResult> getIpapiService(String ip, String key) {
        return retrofit.create(IpapiService.class).getIpapiResult(ip, key);
    }

    public Single<IpAddress> getIPResult() {
        return retrofit.create(IPService.class).getMyIp();
    }


}

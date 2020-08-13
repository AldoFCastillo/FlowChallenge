package com.example.FlowChallenge.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.FlowChallenge.dataSource.WeatherDataSource;
import com.example.FlowChallenge.model.WeatherForecastResult;
import com.example.FlowChallenge.model.WeatherTodayResult;


public class WeatherViewModel extends ViewModel {


    public MutableLiveData<WeatherForecastResult> data;
    public MutableLiveData<WeatherTodayResult> dataToday;
    public MutableLiveData<Boolean> loading;
    public MutableLiveData<Boolean> error;
    private WeatherDataSource weatherDataSource = new WeatherDataSource();

    public WeatherViewModel() {
    }


    public void getWeatherForecastResult(String city) {
        data = weatherDataSource.refreshGetForecastResult(city);
        loading = weatherDataSource.getLoading();
        error = weatherDataSource.getError();
    }
    public void getWeatherTodayResult(String city) {
        dataToday = weatherDataSource.refreshGetTodayResult(city);
        loading = weatherDataSource.getLoading();
        error = weatherDataSource.getError();
    }
}

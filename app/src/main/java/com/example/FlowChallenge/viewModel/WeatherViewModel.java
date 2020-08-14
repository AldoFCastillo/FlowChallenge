package com.example.FlowChallenge.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.FlowChallenge.dataSource.WeatherDataSource;
import com.example.FlowChallenge.model.WeatherResult;


public class WeatherViewModel extends ViewModel {

    public MutableLiveData<WeatherResult> data;
    public MutableLiveData<Boolean> loading;
    public MutableLiveData<Boolean> error;
    private WeatherDataSource weatherDataSource = new WeatherDataSource();

    public WeatherViewModel() {
    }


    public void getWeatherResult(String lat, String lon){
        data = weatherDataSource.refreshGetWeatherResult(lat, lon);
        loading = weatherDataSource.getLoading();
        error = weatherDataSource.getError();
    }
}

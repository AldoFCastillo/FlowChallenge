package com.example.FlowChallenge.dataSource;

import androidx.lifecycle.MutableLiveData;

import com.example.FlowChallenge.model.WeatherResult;
import com.example.FlowChallenge.service.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherDataSource {

    public static final String API_KEY = "79e508708d93759b5d5adbabca775142";
    public static final String METRIC = "metric";

    public MutableLiveData<WeatherResult> data;
    public MutableLiveData<Boolean> error;
    public MutableLiveData<Boolean> loading;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInstance retrofitInstance = RetrofitInstance.getInstanceWeather();

    private void getWeatherResult(String lat, String lon) {
        data = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        compositeDisposable.add(retrofitInstance.getApiWeatherService(lat, lon, METRIC, API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherResult>() {
                    @Override
                    public void onSuccess(WeatherResult value) {
                        data.setValue(value);
                        loading.setValue(false);
                        error.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        loading.setValue(false);
                        error.setValue(true);
                    }
                }));
    }

    public MutableLiveData<WeatherResult> refreshGetWeatherResult(String lat, String lon) {
        getWeatherResult(lat, lon);
        return data;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }


}

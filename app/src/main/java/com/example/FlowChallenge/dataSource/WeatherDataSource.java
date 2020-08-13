package com.example.FlowChallenge.dataSource;

import androidx.lifecycle.MutableLiveData;

import com.example.FlowChallenge.model.WeatherForecastResult;
import com.example.FlowChallenge.model.WeatherTodayResult;
import com.example.FlowChallenge.service.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class WeatherDataSource {

    public static final String API_KEY = "79e508708d93759b5d5adbabca775142";
    public static final String METRIC = "metric";

    private MutableLiveData<WeatherForecastResult> data;
    private MutableLiveData<WeatherTodayResult> dataToday;
    private MutableLiveData<Boolean> error;
    public MutableLiveData<Boolean> loading;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInstance retrofitInstance = RetrofitInstance.getInstanceWeather();


    private void getForecastResult(String city){
        data = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
       // loading.setValue(true);
        compositeDisposable.add(retrofitInstance.getApiWeatherForecastService(city, METRIC, API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherForecastResult>(){
                    @Override
                    public void onSuccess(WeatherForecastResult value) {
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

    public MutableLiveData<WeatherForecastResult> refreshGetForecastResult(String city){
        getForecastResult(city);
        return data;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getError(){
        return error;
    }

    private void getTodayResult(String city){
        dataToday = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        loading.setValue(true);
        compositeDisposable.add(retrofitInstance.getApiWeatherTodayService(city, METRIC, API_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<WeatherTodayResult>(){
                    @Override
                    public void onSuccess(WeatherTodayResult value) {
                        dataToday.setValue(value);
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

    public MutableLiveData<WeatherTodayResult> refreshGetTodayResult(String city){
        getTodayResult(city);
        return dataToday;
    }


}

package com.example.FlowChallenge;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.FlowChallenge.dataSource.WeatherDataSource;
import com.example.FlowChallenge.model.WeatherResult;
import com.example.FlowChallenge.service.RetrofitInstance;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

public class WeatherDataSourceTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    public RetrofitInstance retrofitInstance;

    @InjectMocks
    public WeatherDataSource weatherDataSource = new WeatherDataSource();
    private Single<WeatherResult> weatherResultSingleTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setupRxSchedulers();
    }

    @Test
    public void testSuccess() {
        String lat = "48.8032";
        String lon = "2.3511";
        WeatherResult weatherResult = new WeatherResult();
        weatherResultSingleTest = Single.just(weatherResult);
        Mockito.when(retrofitInstance
                .getApiWeatherService(lat, lon, WeatherDataSource.METRIC, WeatherDataSource.API_KEY))
                .thenReturn(weatherResultSingleTest);

        weatherDataSource.refreshGetWeatherResult(lat, lon);
        Assert.assertEquals(weatherResult, weatherDataSource.data.getValue());
        Assert.assertEquals(false, weatherDataSource.error.getValue());
        Assert.assertEquals(false, weatherDataSource.loading.getValue());

    }

    @Test
    public void testError() {
        weatherResultSingleTest = Single.error(Throwable::new);
        Mockito.when(retrofitInstance
                .getApiWeatherService("0","ñ", WeatherDataSource.METRIC, WeatherDataSource.API_KEY))
                .thenReturn(weatherResultSingleTest);
        weatherDataSource.refreshGetWeatherResult("0", "ñ");
        Assert.assertEquals(true, weatherDataSource.error.getValue());
        Assert.assertEquals(false, weatherDataSource.loading.getValue());
    }

    private void setupRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        };

        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }
}

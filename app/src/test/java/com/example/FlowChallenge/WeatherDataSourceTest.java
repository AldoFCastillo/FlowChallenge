package com.example.FlowChallenge;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.FlowChallenge.dataSource.IpapiDataSource;
import com.example.FlowChallenge.dataSource.WeatherDataSource;
import com.example.FlowChallenge.model.IpapiResult;
import com.example.FlowChallenge.model.WeatherTodayResult;
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
    private Single<WeatherTodayResult> todayResultSingleTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setupRxSchedulers();
    }

    @Test
    public void testSuccess() {
        WeatherTodayResult weatherTodayResult = new WeatherTodayResult();
        todayResultSingleTest = Single.just(weatherTodayResult);
        Mockito.when(retrofitInstance
                .getApiWeatherTodayService("London", WeatherDataSource.METRIC, WeatherDataSource.API_KEY))
                .thenReturn(todayResultSingleTest);

        weatherDataSource.refreshGetTodayResult("London");
        Assert.assertEquals(weatherTodayResult, weatherDataSource.dataToday.getValue());
        Assert.assertEquals(false, weatherDataSource.error.getValue());
        Assert.assertEquals(false, weatherDataSource.loading.getValue());

    }

    @Test
    public void testError() {
        todayResultSingleTest = Single.error(Throwable::new);
        Mockito.when(retrofitInstance
                .getApiWeatherTodayService("0", WeatherDataSource.METRIC, WeatherDataSource.API_KEY))
                .thenReturn(todayResultSingleTest);
        weatherDataSource.refreshGetTodayResult("0");
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

package com.example.FlowChallenge;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.FlowChallenge.dataSource.IPDataSource;
import com.example.FlowChallenge.model.IpAddress;
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

public class IPDataSourceTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    public RetrofitInstance retrofitInstance;

    @InjectMocks
    public IPDataSource ipDataSource = new IPDataSource();
    private Single<IpAddress> testSingleIP;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setUpRxSchedulers();
    }

    @Test
    public void testSuccess() {
        IpAddress ipAddress = new IpAddress("HOLA");
        testSingleIP = Single.just(ipAddress);
        Mockito.when(retrofitInstance.getIPResult()).thenReturn(testSingleIP);
        ipDataSource.refreshGetIP();
        Assert.assertEquals(ipAddress, ipDataSource.data.getValue());
        Assert.assertEquals(false, ipDataSource.loading.getValue());
        Assert.assertEquals(false, ipDataSource.error.getValue());
    }

    @Test
    public void testError() {
        testSingleIP = Single.error(Throwable::new);
        Mockito.when(retrofitInstance.getIPResult()).thenReturn(testSingleIP);
        ipDataSource.refreshGetIP();
        Assert.assertEquals(false, ipDataSource.loading.getValue());
        Assert.assertEquals(true, ipDataSource.error.getValue());
    }

    public void setUpRxSchedulers() {
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

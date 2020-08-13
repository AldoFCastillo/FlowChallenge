package com.example.FlowChallenge;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.FlowChallenge.dataSource.IpapiDataSource;
import com.example.FlowChallenge.model.IpapiResult;
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

public class IpapiDataSourceTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    public RetrofitInstance retrofitInstance;

    @InjectMocks
    public IpapiDataSource ipapiDataSource = new IpapiDataSource();
    private Single<IpapiResult> ipapiResultSingleTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        setupRxSchedulers();
    }

    @Test
    public void testSuccess(){
        IpapiResult ipapiResult = new IpapiResult();
        ipapiResultSingleTest = Single.just(ipapiResult);
        Mockito.when(retrofitInstance.getIpapiService("200.126.156.147",IpapiDataSource.IPAPI_KEY)).thenReturn(ipapiResultSingleTest);
        ipapiDataSource.refreshGetIpapiResult("200.126.156.147");
        Assert.assertEquals(ipapiResult, ipapiDataSource.data.getValue());
        Assert.assertEquals(false, ipapiDataSource.error.getValue());
    }

    @Test
    public void testError(){
        ipapiResultSingleTest = Single.error(Throwable::new);
        Mockito.when(retrofitInstance.getIpapiService("232",IpapiDataSource.IPAPI_KEY)).thenReturn(ipapiResultSingleTest);
        ipapiDataSource.refreshGetIpapiResult("232");
        Assert.assertEquals(true, ipapiDataSource.error.getValue());

    }

    private void setupRxSchedulers() {
        Scheduler immediate = new Scheduler() {
            @Override
            public Worker createWorker() {
                return new ExecutorScheduler.ExecutorWorker(Runnable::run);
            }
        } ;

        RxJavaPlugins.setInitIoSchedulerHandler(schedulerCallable -> immediate);
        RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
        RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
    }


}

package com.example.FlowChallenge.dataSource;

import androidx.lifecycle.MutableLiveData;

import com.example.FlowChallenge.model.IpAddress;
import com.example.FlowChallenge.service.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class IPDataSource {

    public MutableLiveData<IpAddress> data;
    public MutableLiveData<Boolean> loading;
    public MutableLiveData<Boolean> error;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInstance retrofitInstance = RetrofitInstance.getInstanceIP();

    public IPDataSource() {
    }

    private void getIP() {
        data = new MutableLiveData<>();
        loading = new MutableLiveData<>();
        error = new MutableLiveData<>();
        loading.setValue(true);
        compositeDisposable.add(retrofitInstance.getIPResult()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<IpAddress>() {
                    @Override
                    public void onSuccess(IpAddress value) {
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

    public MutableLiveData<IpAddress> refreshGetIP() {
        getIP();
        return data;
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }
}


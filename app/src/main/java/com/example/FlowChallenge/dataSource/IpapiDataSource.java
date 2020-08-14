package com.example.FlowChallenge.dataSource;

import androidx.lifecycle.MutableLiveData;

import com.example.FlowChallenge.model.IpapiResult;
import com.example.FlowChallenge.service.RetrofitInstance;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;


public class IpapiDataSource {

    public static final String IPAPI_KEY = "44aa76370f472d6ca71f7a91ec43a201";

    public MutableLiveData<IpapiResult> data = new MutableLiveData<>();
    public MutableLiveData<Boolean> error = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInstance retrofitInstance = RetrofitInstance.getInstanceIpapi();

    public IpapiDataSource() {
    }

    private void getIpapiResult(String ip) {
        data = new MutableLiveData<>();
        error = new MutableLiveData<>();
        compositeDisposable.add(retrofitInstance.getIpapiService(ip, IPAPI_KEY)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<IpapiResult>() {
                    @Override
                    public void onSuccess(IpapiResult value) {
                        data.setValue(value);
                        error.setValue(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        error.setValue(true);
                    }
                })

        );

    }

    public MutableLiveData<IpapiResult> refreshGetIpapiResult(String ip) {
        getIpapiResult(ip);
        return data;
    }

    public MutableLiveData<Boolean> getError() {
        return error;
    }


}

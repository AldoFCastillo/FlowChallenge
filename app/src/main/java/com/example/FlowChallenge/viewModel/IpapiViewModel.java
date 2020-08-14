package com.example.FlowChallenge.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.FlowChallenge.dataSource.IpapiDataSource;
import com.example.FlowChallenge.model.IpapiResult;


public class IpapiViewModel extends ViewModel {


    public MutableLiveData<IpapiResult> data;
    public MutableLiveData<Boolean> error;
    private IpapiDataSource ipapiDataSource = new IpapiDataSource();


    public void getIpapiResult(String ip) {
        data = ipapiDataSource.refreshGetIpapiResult(ip);
        error = ipapiDataSource.getError();
    }
}

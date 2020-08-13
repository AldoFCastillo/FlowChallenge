package com.example.FlowChallenge.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.FlowChallenge.dataSource.IPDataSource;
import com.example.FlowChallenge.model.IpAddress;


public class IPViewModel extends ViewModel {

    public MutableLiveData<IpAddress> data;
    public MutableLiveData<Boolean> loading;
    public MutableLiveData<Boolean> error;

    private IPDataSource ipDataSource = new IPDataSource();


    public void getIP(){
        data = ipDataSource.refreshGetIP();
        loading = ipDataSource.getLoading();
        error = ipDataSource.getError();
    }


}

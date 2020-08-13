package com.example.FlowChallenge.service;

import com.example.FlowChallenge.model.IpAddress;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface IPService {
    @GET("/?format=json")
    Single<IpAddress> getMyIp();
}




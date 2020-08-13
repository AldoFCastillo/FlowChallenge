package com.example.FlowChallenge.service;

import com.example.FlowChallenge.model.IpapiResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IpapiService {

    @GET("{ip}?")
    Single<IpapiResult> getIpapiResult (@Path("ip") String ip, @Query("access_key") String access_key);
}

package com.example.FlowChallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FeelsLike implements Serializable {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("night")
    @Expose
    private String night;
    @SerializedName("eve")
    @Expose
    private String eve;
    @SerializedName("morn")
    @Expose
    private String morn;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getEve() {
        return eve;
    }

    public void setEve(String eve) {
        this.eve = eve;
    }

    public String getMorn() {
        return morn;
    }

    public void setMorn(String morn) {
        this.morn = morn;
    }
}

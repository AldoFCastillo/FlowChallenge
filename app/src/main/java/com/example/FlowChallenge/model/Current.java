package com.example.FlowChallenge.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Current implements Serializable {

    @SerializedName("dt")
    @Expose
    private  String dt;
    @SerializedName("sunrise")
    @Expose
    private  String sunrise;
    @SerializedName("sunset")
    @Expose
    private  String sunset;
    @SerializedName("temp")
    @Expose
    private String temp;
    @SerializedName("feels_like")
    @Expose
    private String feelsLike;
    @SerializedName("pressure")
    @Expose
    private  String pressure;
    @SerializedName("humidity")
    @Expose
    private  String humidity;
    @SerializedName("dew_point")
    @Expose
    private String dewPoint;
    @SerializedName("uvi")
    @Expose
    private String uvi;
    @SerializedName("clouds")
    @Expose
    private  String clouds;
    @SerializedName("visibility")
    @Expose
    private  String visibility;
    @SerializedName("wind_speed")
    @Expose
    private String windSpeed;
    @SerializedName("wind_deg")
    @Expose
    private  String windDeg;
    @SerializedName("weather")
    @Expose
    private List<Weather> weather = null;
   /* @SerializedName("rain")
    @Expose
    private Rain rain;*/


    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(String dewPoint) {
        this.dewPoint = dewPoint;
    }

    public String getUvi() {
        return uvi;
    }

    public void setUvi(String uvi) {
        this.uvi = uvi;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWindDeg() {
        return windDeg;
    }

    public void setWindDeg(String windDeg) {
        this.windDeg = windDeg;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
}

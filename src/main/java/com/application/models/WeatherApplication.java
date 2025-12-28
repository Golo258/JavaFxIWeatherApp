package com.application.models;

import java.util.List;

public class WeatherApplication {


    public List<Daily> daily;

    public WeatherApplication() {
    }

    public WeatherApplication(List<Daily> daily) {
        this.daily = daily;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

}

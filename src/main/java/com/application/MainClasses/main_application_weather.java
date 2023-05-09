package com.application.MainClasses;

import java.util.List;

public class main_application_weather {


    public List<Daily> daily;

    public main_application_weather() {
    }

    public main_application_weather( List<Daily> daily) {
        this.daily = daily;
    }

    public List<Daily> getDaily() {
        return daily;
    }

    public void setDaily(List<Daily> daily) {
        this.daily = daily;
    }

}

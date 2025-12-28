package com.application.models;

public class FeelsLike {

    private double day;
    private double night; 
    private double eve;
    private double morn;

    public FeelsLike() {
    }

    public FeelsLike(double day, double night, double eve, double morn) {
        this.day = day;
        this.night = night;
        this.eve = eve;
        this.morn = morn;
    }

    public double getDay() {
        return day;
    }

    public void setDay(double day) {
        this.day = day;
    }

    public double getNight() {
        return night;
    }

    public void setNight(double night) {
        this.night = night;
    }

    public double getEve() {
        return eve;
    }

    public void setEve(double eve) {
        this.eve = eve;
    }

    public double getMorn() {
        return morn;
    }

    public void setMorn(double morn) {
        this.morn = morn;
    }

    public String show(String type) {
        String builder;
        if (type.equals("short")) {
            builder = "Day: " + this.getDay() + "\nNight: " + this.getNight();
        } else {
            builder = "Day: " + this.getDay() + "\tNight: " + this.getNight() +
                    "\tMorn: " + this.getMorn() + "\tEve " + this.getEve() + "\n";
        }
        return builder;
    }
}

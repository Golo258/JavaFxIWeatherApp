package com.application.MainClasses;

public class temp {

    private double day;
    private double min;
    private double max;
    private double night;
    private double eve;
    private double morn;

    public temp() {
    }

    public temp(double day, double min, double max, double night, double eve, double morn) {
        this.day = day;
        this.min = min;
        this.max = max;
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

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
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
            builder = "Min: " + this.getMin() + "\nMax: " + this.getMin();
        } else {
            builder = "Min: " + this.getMin() + "\tMax: " + this.getMax() +
                    "\tMorning: " + this.getMorn() + "\tEvening " + this.getEve() + "\n";
        }
        return builder;
    }
}

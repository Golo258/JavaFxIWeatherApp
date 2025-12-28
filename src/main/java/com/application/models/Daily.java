package com.application.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Daily {
    private int sunrise;
    private int sunset;
    private int moonrise;
    private int moonset;
    private double moon_phase;
    private Temp temp;
    private FeelsLike FeelsLike;
    private int pressure;
    private int humidity;
    private double wind_speed;
    private int wind_deg;
    private List<Weather> weather;

    public Daily() {
    }

    public Daily(int sunrise, int sunset, int moonrise, int moonset, double moon_phase, Temp temp, FeelsLike FeelsLike, int pressure, int humidity, double wind_speed, int wind_deg, List<Weather> weather) {
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.moonrise = moonrise;
        this.moonset = moonset;
        this.moon_phase = moon_phase;
        this.temp = temp;
        this.FeelsLike = FeelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.wind_speed = wind_speed;
        this.wind_deg = wind_deg;
        this.weather = weather;
    }

    public String show(String type, String options) {
        String builder;
        if (options.equals("stamps")) {
            if (type.equals("short")) {
                builder = "Sunrise: " + this.getSunrise() + "\nSunset: " + this.getSunset();
            } else {
                builder = "Sunrise: " + this.getSunrise() + "\tSunset: " + this.getSunset() +
                        "\tMoonset: " + this.getMoonset() + "\tMoonrise " + this.getMoonrise() + "\n";
            }
        } else{ // simple
            if (type.equals("short")) {
                builder = "Humidity: " + this.getHumidity() + "\nPressure: " + this.getPressure();
            } else {
                builder = "Humidity: " + this.getHumidity() + "\tPressure: " + this.getPressure() +
                        "\tWind speed: " + this.getWind_speed() + "\tWind deg " + this.getWind_deg() + "\n";
            }
        }
        return builder;
    }

    public String convertTimesStamps(int timestamp) {
        Instant instant = Instant.ofEpochSecond(timestamp);
        LocalDateTime localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm:ss");
        return localDate.format(format);
    }

    public String getSunrise() {
        return convertTimesStamps(sunrise);
    }


    public String getSunset() {
        return convertTimesStamps(sunset);
    }
    public String getMoonrise() {
        return convertTimesStamps(moonrise);
    }

    public String getMoonset() {
        return convertTimesStamps((int)moonset);
    }
    public String getMoon_phase() {
        return convertTimesStamps((int)moon_phase);
    }
    public Temp getTemp() {
        return temp;
    }


    public FeelsLike getFeels_like() {
        return FeelsLike;
    }


    public int getPressure() {
        return pressure;
    }


    public int getHumidity() {
        return humidity;
    }


    public double getWind_speed() {
        return wind_speed;
    }


    public int getWind_deg() {
        return wind_deg;
    }


    public List<Weather> getWeather() {
        return weather;
    }

}

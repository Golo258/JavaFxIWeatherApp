package com.application.models;

import java.util.ArrayList;

public class Countries {
    private String name;
    private ArrayList<String> cities;

    public Countries() {
    }

    public Countries(String name, ArrayList<String> cities) {
        this.name = name;
        this.cities = cities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getCities() {
        return cities;
    }

    public void setCities(ArrayList<String> cities) {
        this.cities = cities;
    }
}

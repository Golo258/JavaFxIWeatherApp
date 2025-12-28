package com.application.main;

import com.application.exceptions.*;
import com.application.models.Daily;
import com.application.models.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.BufferedReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GettingApiData {
    private final static String API_BASE_URL = "https://api.openweathermap.org/data/2.5/onecall";
    private final static String API_KEY = "6f7a9e39527eb8ae006650cfe4ac4fa7";
    private final static String API_LOCATION_KEY = "22314352069847138a69205732e35936";
    private ObjectMapper mapper = getDefaultMapper();
    private File jsonCountriesFile = new File(
        getClass().getResource(
            "/fxmlGUIbuilder/countries.json"
        )
    )

    public ArrayList<Double> getApiCoordinates(String locationName) throws AppExceptions {
        Pattern regex = Pattern.compile("^[a-zA-Z]{3,10}, [a-zA-Z\s]{3,20}$");
        Matcher regexMatcher = regex.matcher(locationName);
        ArrayList<Double> coordinates = new ArrayList<>();
        if (regexMatcher.find()) {
            String url = "https://api.opencagedata.com/geocode/v1/json?q="
                + URLEncoder.encode(locationName, StandardCharsets.UTF_8)
                + "&key=" + API_LOCATION_KEY;
            System.out.println(url);

            try {
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
                );
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                connection.disconnect();
                JsonNode node = new ObjectMapper().readTree(content.toString());

                double lat = node
                    .get("results")
                    .get(0)
                    .get("geometry")
                    .get("lat")
                    .asDouble();
                double lng = node.get("results")
                    .get(0)
                    .get("geometry")
                    .get("lng")
                    .asDouble();
                coordinates.add(lat);
                coordinates.add(lng);
            } catch (Exception exception) {
                throw new PlaceExistingException("Invalid URL");
            }
        } else {
            throw new LocalisationSyntaxException("Math not found");
        }

        return coordinates;
    }

    public WeatherApplication getApiValues(ArrayList cords) throws IOException, AppExceptions{
        if (!cords.isEmpty()) {
            if (!cords.stream().map(element -> element instanceof Double).
                    toList().contains(false)) {
                String url = API_BASE_URL +
                    "?lat=" + cords.get(0) +
                    "&lon=" + cords.get(1) +
                    "&exclude=minutely,hourly" +
                    "&units=metric" +
                    "&appid=" + API_KEY;
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                        connection.getInputStream()
                    )
                );
                String inputLine;
                StringBuffer content = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                connection.disconnect();
                JsonNode node = new ObjectMapper().readTree(content.toString());

                WeatherApplication main_weather = new WeatherApplication();
                ArrayNode dailyArrayNode = (ArrayNode) node.get("daily");
                ArrayList<Daily> dailies = new ArrayList<>();
                for (JsonNode dailyNode : dailyArrayNode) {
                    ObjectNode tempObjNode = (ObjectNode) dailyNode.get("temp");
                    ArrayNode weatherArrayNode = (ArrayNode) dailyNode.get("weather");
                    ObjectNode feelsLikeNode = (ObjectNode) dailyNode.get("feels_like");
                    Temp tempObject = new Temp(
                        tempObjNode.get("day").asDouble(),
                        tempObjNode.get("min").asInt(),
                        tempObjNode.get("max").asDouble(),
                        tempObjNode.get("night").asDouble(),
                        tempObjNode.get("eve").asDouble(),
                        tempObjNode.get("morn").asDouble()
                    );

                    List<Weather> weatherArray = new ArrayList<>();
                    for (JsonNode weatherNode : weatherArrayNode) {
                        weatherArray.add(new Weather(weatherNode.get("icon").asText()));
                    }
                    FeelsLike feelsLikeObject = new FeelsLike(
                        feelsLikeNode.get("day").asDouble(),
                        feelsLikeNode.get("night").asDouble(),
                        feelsLikeNode.get("eve").asDouble(),
                        feelsLikeNode.get("morn").asDouble()
                    );

                    dailies.add(new Daily(
                        dailyNode.get("sunrise").asInt(),
                        dailyNode.get("sunset").asInt(),
                        dailyNode.get("moonrise").asInt(),
                        dailyNode.get("moonset").asInt(),
                        dailyNode.get("moon_phase").asDouble(),
                        tempObject,
                        feelsLikeObject,
                        dailyNode.get("pressure").asInt(),
                        dailyNode.get("humidity").asInt(),
                        dailyNode.get("wind_speed").asDouble(),
                        dailyNode.get("wind_deg").asInt(),
                        weatherArray
                    ));
                }
                main_weather.setDaily(dailies);
                return main_weather;
            } else {
                throw new InvalidCordTypeException("Wrong type of arguments");
            }
        } else {
            throw new NotGiveCordTypeException("Cords are empty");
        }
    }

    public ArrayList<String> getCountriesListFromJson() {
        ArrayList<String> countriesList = new ArrayList<>();
        try {
            Countries[] countries = mapper.readValue(
                jsonCountriesFile, Countries[].class
            );
            for (Countries country : countries) {
                countriesList.add(country.getName());
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return countriesList;
    }

    public ArrayList<String> getCitiesBasedOnCountries(String countryName) throws IOException {
        ArrayList<String> cities = new ArrayList<>();
        try {
            Countries[] countries = mapper.readValue(
                jsonCountriesFile, Countries[].class
            );
            for (Countries count : countries) {
                if (count.getName().contains(countryName)) // TODO regex from textField
                    cities = count.getCities();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return cities;
    }


    public ObjectMapper getDefaultMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(
            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false
        );
        return mapper;
    }

}
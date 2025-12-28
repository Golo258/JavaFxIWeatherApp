package com.application.tests;


import com.application.models.Countries;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.util.*;

public class ApiControllerTest {
    private final static String API_LOCATION_KEY = "22314352069847138a69205732e35936";
    enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public HashMap<String, ArrayList<String>>  getCountriesList() throws IOException {
        HashMap<String, ArrayList<String>> mapOfCountries = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        File file = new File(
            getClass().getResource(
                "/fxmlGUIbuilder/countries.json"
            )
        );
        try {
            Countries[] countries = mapper.readValue(file, Countries[].class);
            for (Countries country : countries) {
                mapOfCountries.put(country.getName(), country.getCities());
            }
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return mapOfCountries;
    }

    public static void testAdding() {
        Set<String> singleTon = Collections.singleton("Siemano");
        System.out.println("Singleton : " + singleTon);
        List<String> test = new ArrayList<>(
            Collections.singleton(
                Arrays.toString(WeekDay.values())
            )
        );
        System.out.println("Test: " + test + "\n--------\n");
        String chosenDay = "WEDNESDAY";
        WeekDay day = WeekDay.valueOf(chosenDay);
        System.out.println(day.ordinal());

        String[] days = Arrays
            .stream(WeekDay.values())
            .map(Enum::toString)
            .toArray(String[]::new);
        for (String val : days) System.out.println(val); 
    }

    public static ArrayList<String> getLocationFromApi(String locationName) throws IOException {
        String url = "https://api.opencagedata.com/geocode/v1/json?q="
            + URLEncoder.encode(locationName, "UTF-8")
            + "&key=" + API_LOCATION_KEY;
        System.out.println("URL2: " + url);
        ArrayList<String> coordinates = new ArrayList<>();
        URL apiURL = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException(
                "Failed : HTTP error code : " + conn.getResponseCode()
            );
        }
        JsonElement json = new JsonParser().parse(
            new InputStreamReader((InputStream) conn.getContent())
        );
        Map<String, Object> responseMap = new HashMap<>();
        JsonObject jsonResponse = new Gson().toJsonTree(responseMap).getAsJsonObject();

        for (String cordName : new String[]{"lat", "lng"}) {
            coordinates.add(jsonResponse
                .getAsJsonObject("results")
                .getAsJsonArray("items")
                .get(0).getAsJsonObject()
                .get("geometry").getAsJsonObject()
                .get(cordName).getAsJsonObject()
                .get("value").getAsJsonObject()
                .get("value").getAsJsonPrimitive()
                .getAsJsonPrimitive()
                .getAsString()
            );
        }
    }
    
    public static void GetApiICon(String icon) {
        try {
            String iconURL = "http://openweathermap.org/img/w/" + icon + ".png";
            URL url = new URL(iconURL);
            System.out.println(url);
            int byteRead = 0;
            byte[] buffer = new byte[4096];
            InputStream inputStream = url.openStream();
            FileOutputStream outputStream = new FileOutputStream(
                getClass().getResource("/Images/icon.png")
            );
            while ((byteRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteRead);
            }
            outputStream.close();
            inputStream.close();
        } 
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void converBigDecimal() {
        BigDecimal big = new BigDecimal("1235.512");
        double dub = big.doubleValue();
        System.out.println(dub);
    }

}

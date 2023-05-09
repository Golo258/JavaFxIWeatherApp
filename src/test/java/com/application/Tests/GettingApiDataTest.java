package com.application.Tests;

import com.application.Application.GettingApiData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import com.application.MainClasses.main_application_weather;
import static org.junit.jupiter.api.Assertions.*;

class GettingApiDataTest {
//    GIVEN

//    WHEN

    //    Then
    private GettingApiData data = new GettingApiData();

    @Test
    public void testGettingApiCoordinatesNegativeScenario() {

        String mathLocation = "BadLocation";
        String urlLocation = "Bad, Argument";
        Throwable matchException = assertThrows(
                Exception.class, () -> {
                    ArrayList<Double> coordinates = data.getApiCoordinates(mathLocation);
                }
        );
        String expectedMath = "Math not found";
        assertEquals(expectedMath, matchException.getMessage());
        System.out.println(expectedMath);
        //        Second posibility
        Throwable urlException = assertThrows(
                Exception.class, () -> {
                    ArrayList<Double> coordinates = data.getApiCoordinates(urlLocation);
                }
        );
        String expectedURL = "Invalid URL";
        assertEquals(expectedURL, urlException.getMessage());
        System.out.println(expectedURL);
    }

    @Test
    public void testGettingApiCoordinatesPositiveScenario() throws Exception {
        String locationName = "Poland, Cracow";
        String locationName2 = "USA, Chicago";
        ArrayList<Double> coordinates = data.getApiCoordinates(locationName);
        ArrayList<Double> coordinatesv2 = data.getApiCoordinates(locationName2);
        assertNotNull(coordinates);

        coordinatesv2.stream().forEach(element -> System.out.println("Elemnt is Double : " + (element instanceof Double)));
        boolean condition = (coordinatesv2.get(0) instanceof Double && coordinatesv2.get(1) != null);
        assertTrue(condition);
    }

    @Test
    public void testGettingApiValuesNegativeScenario() throws Exception {

        // Type check
        ArrayList<Integer> cords = new ArrayList<>(Arrays.asList(
                12, 15
        ));

        Throwable urlException = assertThrows(
                Exception.class, () -> {
                  main_application_weather weather = data.getApiValues(cords);
                }
        );
        String typeException = "Wrong type of arguments";
        assertEquals(typeException, urlException.getMessage());

        // is Empty check
        ArrayList<Double> emptyCords = new ArrayList<>();

        assertThrows(Exception.class, () -> {
            main_application_weather weather = data.getApiValues(cords);
        }, "Cords are empty");
    }
    @Test
    public void testGettingApiValuesPositiveScenario() throws Exception {
        ArrayList<Double> cords = new ArrayList<>(Arrays.asList(
                25.5, 32.5
        ));

        main_application_weather weather = data.getApiValues(cords);

        assertNotNull(weather);
    }

}
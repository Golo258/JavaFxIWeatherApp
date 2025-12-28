package com.application;


import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.util.*;
import java.util.stream.Collectors;

public class testClass {
    enum WeekDay {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }

    public static void main(String[] args) throws IOException {
//        getCountriesList();
    }

//    public static HashMap<String, ArrayList<String>>  getCountriesList() throws IOException {
//        HashMap<String, ArrayList<String>> mapOfCountries = new HashMap<>();
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule());
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        File file = new File("C:\\Users\\Grzesiek\\Desktop\\Repositories\\Java_Projects\\JavaFxIWeatherApp\\src\\main\\resources\\fxmlGUIbuilder\\countries.json");
//        try {
//            Countries[] countries = mapper.readValue(file, Countries[].class);
//            for (Countries country : countries) {
//                mapOfCountries.put(country.getName(), country.getCities());
//            }
//        }
//        catch (Exception exception){
//            System.out.println(exception.getMessage());
//        }
//        return mapOfCountries;
//    }

    public static void testAdding() {
//        Singleton -> set of one value
        Set<String> singleTon = Collections.singleton("Siemano");
        System.out.println("Singleton : " + singleTon);
//        System.out.println(Arrays.toString(WeekDay.values()));
        List<String> test = new ArrayList<>(Collections.singleton(Arrays.toString(WeekDay.values())));
        System.out.println("Test: " + test + "\n--------\n");
        String chosenDay = "WEDNESDAY";
        WeekDay day = WeekDay.valueOf(chosenDay);
        System.out.println(day.ordinal());

//
        String[] days = Arrays.stream(WeekDay.values())
                .map(Enum::toString)
                .toArray(String[]::new);
        for (String val : days) {
            System.out.println(val);
        }
    }

    //    public static void gettingApiOld(){
//        public static ArrayList<String> getLocationFromApi(String locationName) throws IOException {
////        coordinates are needed to make other responde
////        String locationName = "New York City";
//            String url = "https://api.opencagedata.com/geocode/v1/json?q="
//                    + URLEncoder.encode(locationName, "UTF-8")
//                    + "&key=" + API_LOCATION_KEY;
//            System.out.println("URL2: " + url);
//            ArrayList<String> coordinates = new ArrayList<>();
//            try {
//                URL apiURL = new URL(url);
//                HttpURLConnection conn = (HttpURLConnection) apiURL.openConnection();
//                conn.setRequestMethod("GET");
//                conn.setRequestProperty("Accept", "application/json");
//
//                if (conn.getResponseCode() != 200) {
//                    throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
//                }
//                JsonElement json = new JsonParser().parse(
//                        new InputStreamReader((InputStream) conn.getContent()));
//                Map<String, Object> responseMap = jsonToMap(json);
//                JsonObject jsonResponse = new Gson().toJsonTree(responseMap).getAsJsonObject();
//
//                for (String cordName : new String[]{"lat", "lng"}) {
//                    coordinates.add(jsonResponse.getAsJsonObject("results")
//                            .getAsJsonArray("items")
//                            .get(0).getAsJsonObject()
//                            .get("geometry").getAsJsonObject()
//                            .get(cordName).getAsJsonObject()
//                            .get("value").getAsJsonObject()
//                            .get("value").getAsJsonPrimitive()
//                            .getAsJsonPrimitive()
//                            .getAsString());
////                Poland,Cracow
//                }
//            } catch (IOException | JsonIOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    public static void tryingStreaming() {
//        map ->
        List<Integer> number = Arrays.asList(2, 3, 4, 5);
        List<Integer> square = number.stream().map(value -> value * 2).toList();
        System.out.println("Map: " + number + square);

//        filter ->
        List<String> names = Arrays.asList("Siemano", "kolano", "Co tam");
        List<String> results = names.stream().filter(word -> word.toLowerCase().startsWith("s")).toList();
        System.out.println("Filter : " + names + results);

//        sorted ->
        List<Integer> grades = Arrays.asList(5, 4, 61, 3, 7, 12, 1);
        List<Integer> sortedGrades = grades.stream().sorted().toList();
        System.out.println("Sorted : " + grades + sortedGrades);

//    collect -> returns proper collections
        List<Integer> numbers2 = Arrays.asList(2, 3, 4, 5, 51);
        Set<Integer> triangle = numbers2.stream()
                .map(numb -> numb * grades.get(2) * 3).collect(Collectors.toSet());
        System.out.println("Collect set : " + numbers2 + triangle);

//    foreach ->
        numbers2.stream().map(val -> val % 2).forEach(
                value -> System.out.println("Value: " + value)
        );
//        reduce -> reduce all items to one
        int reducing = numbers2.stream().filter(val -> val / 6 == 0)
                .reduce(0, (arg, iter) -> arg + iter * square.get(1));
        System.out.println("reducing : " + reducing);

//        Optional class - >to deal with null pointer exception
        Optional<String> optionalName = Optional.of("Allice");
        if (optionalName.isPresent()) {
            String name = optionalName.get();
            System.out.println("Name: " + name);
        } else {
            System.out.println("Name no provided");
        }
        int count = results.stream().
                filter(word -> word.length() > 5).mapToInt(String::length).sum();
        System.out.println("Sum of letters in words: " + count);

//        find first or else
        List<String> fruits = Arrays.asList("apple", "banana", "", "kiwi");
        Optional<String> optionalFruit = fruits.stream()
                .filter(fruit -> !fruit.isEmpty())
                .findFirst()
                .orElse("Fruit is empty").describeConstable();
        System.out.println("Optional fruit " + optionalFruit + "\n\n------------------------------------\n");

    }

    public static void showingEnum() {

//        Equal ignore case test
        String one = "siemano", two = "kolano", third = "SIEmanO";
        System.out.println("Ignore one " + one.equalsIgnoreCase(two));
        System.out.println("Ignore two " + one.equalsIgnoreCase(third));
        //
        String chossen = "monday";
        WeekDay wed = WeekDay.WEDNESDAY;
        System.out.println(wed);
        System.out.println("First " + Arrays.stream(WeekDay.values()).findFirst());

        WeekDay day = Arrays.stream(
                        WeekDay.values()).filter(value -> value.name().equalsIgnoreCase(chossen)).
                findFirst().
                orElse(null);
        System.out.println("Values: " + Arrays.toString(WeekDay.values()));
        System.out.println("Day: " + day);
        if (day != null) {
            System.out.println(chossen + " -> " + day.ordinal());
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
            FileOutputStream outputStream = new FileOutputStream("C:\\Users\\Grzesiek\\Desktop\\Programowanie\\Java" +
                    "\\FXjavaGUI\\src\\main\\resources\\Images\\icon.png");
            while ((byteRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, byteRead);
            }
            outputStream.close();
            inputStream.close();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void converBigDecimal() {
        BigDecimal big = new BigDecimal("1235.512");
        double dub = big.doubleValue();
        System.out.println(dub);
    }

    public static void gettingValueFromHashMap() {
        Map<String, Object> objectMap = new HashMap<>();
        Map<String, String> stringsMap = new HashMap<>();
        objectMap.put("Siemano", Arrays.asList("Siemano", "Kolano"));
        stringsMap.put("Siemaf123no", "Kolano");
        stringsMap.put("124Siemaf123no", "Kol124ano");
        stringsMap.put("Siemaf112423no", "Kola1246no");
        for (Map.Entry<String, String> elements : stringsMap.entrySet()) {
            System.out.println(elements.getKey() + " | | " + elements.getValue());
        }
        System.out.println(objectMap);
        System.out.println(stringsMap);
    }

    public static void usingStringBuilder() {
        StringBuilder result = new StringBuilder();

        result.append("Siemaon");
        System.out.println(result);
        result.insert(5, "kolanko");
        System.out.println(result);
        result.replace(6, 11, "Kizia");
        System.out.println(result);
        result.delete(0, 6);
        System.out.println(result);
    }

    public static void usingBufferedReader() throws UnsupportedEncodingException {
        try {
            InputStream input = new FileInputStream("C:\\Users\\Grzesiek\\Desktop\\Programowanie\\Java\\FXjavaGUI\\src\\main\\java\\module-info.java");
//        getting stream bytes from file
            InputStreamReader reader = new InputStreamReader(
                    input, "UTF-8"
            ); // it converts bytes to utf characters
            BufferedReader buffor = new BufferedReader(reader);
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            while ((line = buffor.readLine()) != null) {
                stringBuilder.append(line);
            }
            System.out.println(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void usingURL() {
        System.out.println("Siemano");
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");// optional

            BufferedReader buffer = new BufferedReader(
                    (new InputStreamReader(connection.getInputStream())
                    ));
            String inputLine;
            StringBuilder respone = new StringBuilder();
            while ((inputLine = buffer.readLine()) != null) {
                respone.append(inputLine);
            }
            buffer.close();
            System.out.println(respone.toString());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

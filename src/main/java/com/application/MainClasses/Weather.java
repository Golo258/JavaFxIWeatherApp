package com.application.MainClasses;

import javafx.scene.image.Image;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Weather {
    private String icon;

    public Weather() {
    }

    public Weather(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static Image getApiIcon(String icon) {
        try {
            String iconURL = "http://openweathermap.org/img/w/" + icon + ".png";
            URL url = new URL(iconURL);
            InputStream inputStream = url.openStream();
            Path path = Paths.get("C:\\Users\\Grzesiek\\Desktop\\Repositories\\Java_Projects\\JavaFxIWeatherApp\\src\\main\\resources\\Images\\icon.png");
            Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("Image saved to: " + path);

            return new Image(path.toUri().toString());

        } catch (Exception e) {
            System.err.println("Malformed URL: " + e.getMessage());
            return null;
        }
    }
}
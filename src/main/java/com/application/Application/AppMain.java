package com.application.Application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class AppMain extends Application {

    Parent rootNode;
    public boolean exitRequest = false;

    public static void main(String[] args) throws IOException {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {

            rootNode = FXMLLoader.load(
                    Objects.requireNonNull(getClass().getResource("/fxmlGUIbuilder/weather_application_scene.fxml"))); // first scene
            String imgURL = "C:\\Users\\Grzesiek\\Desktop\\Repositories\\Java_Projects\\JavaFxIWeatherApp\\src\\main\\resources\\Images\\Icons\\app_icon.png";
            Image weatherIcon = new Image(  imgURL);
            Scene scene = new Scene(rootNode);

            String css_path = Objects.requireNonNull(
                    this.getClass().getResource("/fxmlGUIbuilder/application_styles.css")).toExternalForm();

            scene.getStylesheets().add(css_path);
            stage.setTitle("Weather Application App");
            stage.setScene(scene);
            stage.getIcons().add(weatherIcon);
            stage.show();
            exitWindow();

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    public void exitWindow(){
        if (exitRequest){
            Platform.exit();
        }
        else {
            System.out.println("Request not set");
        }
    }


}

package com.application.main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
                Objects.requireNonNull(getClass().getResource(
                    "/builder/application_scene.fxml")
                )
            ); // first scene
            String appIconPath = String.valueOf(Objects.requireNonNull(
                getClass().getResource(
                    "/static/icons/app_icon.png"
                )
            ));
            Image weatherIcon = new Image(appIconPath);
            Scene scene = new Scene(rootNode);
            String css_path = Objects.requireNonNull(
                this.getClass().getResource(
                    "/builder/application_styles.css"
                )
            ).toExternalForm();

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

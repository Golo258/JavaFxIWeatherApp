package com.application.controllers;

import com.application.exceptions.AppExceptions;
import com.application.main.AppMain;
import com.application.models.*;
import com.application.main.GettingApiData;
import com.application.exceptions.LocalisationSyntaxException;
import com.application.exceptions.NotGivenInputException;
import com.application.exceptions.PlaceExistingException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.List;

public class WeatherAppController implements Initializable {

    @FXML
    AnchorPane mainAnchorPane;
    @FXML
    TextField cordField, secondSceneCordField;
    @FXML
    ImageView weatherIcon;
    @FXML
    CheckBox tempCheck, feelCheck, stampCheck, simpleCheck;
    @FXML
    Label basicLabel, tempDataLabel, feelsDataLabel, stampsDataLabel, simpleDataLabel,
            isGeneratedDataLabel, isGeneratedDataSecondScene;
    @FXML
    GridPane mainGridPane;
    @FXML
    ListView<String> dayListView, countriesView, citiesView;
    @FXML
    GridPane firstDayGridPane, secondDayGridPane,
            thirdDayGridPane, fourthDayGridPane, fifthDayGridPane;

    @FXML
    Button hintButton;

    ArrayList<Label> labelArray = new ArrayList<>(),
            secondSceneLabelArray = new ArrayList<>();
    ArrayList<CheckBox> boxesArray = new ArrayList<>();
    StringBuilder fileDatabuilder = new StringBuilder();

    //---------------------------------------
    int weekDay;
    boolean isDataChanged = false, isChangedSecondScene = false;
    boolean isDataGenerated = false, isDataGeneratedSecondScene = false;
    boolean isHintButtonClicked = false;
    boolean isSecondSceneButtonClicked = false;

    public enum WeekDay {
        MONDAY,
        TUESDAY, WEDNESDAY, THURSDAY,
        FRIDAY, SATURDAY, SUNDAY
    }

    //    Switch to another scenes
    private Stage stage;
    private Scene scene;
    private Parent root;

    //    ---------------------
    private final GettingApiData getData = new GettingApiData();
    private WeatherApplication mainWeather;
    private Daily dailyDay;
    private int defaultFontSize = 18;
    private final String defaultBackGround = "-fx-background-color: #0c8181;";

    private File newSelectedFile;
    private boolean isFirstInit;
    private ArrayList<String> previousCuttedList = new ArrayList<>();

    public void postLocalisation(ActionEvent event) throws AppExceptions {
        if (isDataChanged && boxesArray != null && labelArray != null) { // is false
            for (CheckBox box : boxesArray) {
                box.setSelected(false); // unselect all checkboxes and label data
            }
            for (Label label : labelArray) {
                label.setText("");
            }
            isFirstInit = false;
        } else {
            isFirstInit = true;
        }
        if (!cordField.getText().isEmpty()) {
            try {
                ArrayList<Double> cords = getData.getApiCoordinates(cordField.getText());
                try {
                    if (isGeneratedDataLabel != null) {
                        isGeneratedDataLabel.setText("");
                        isGeneratedDataLabel.setStyle("-fx-border-color: transparent");
                    }
                    mainWeather = getData.getApiValues(cords);
                    isDataGenerated = true;
                    List<Daily> weathersDailies = mainWeather.getDaily();
                    dailyDay = weathersDailies.get(weekDay);
                    String icon = dailyDay.getWeather().get(0).getIcon();
                    weatherIcon.setImage(Weather.getApiIcon(icon));
                    isDataChanged = true;
                    labelArray = new ArrayList<>();
                    boxesArray = new ArrayList<>();
//                TODO consider to change it to something different
                    basicLabel.setText(dailyDay.getTemp().show("long"));
                    labelArray.add(basicLabel);

                } catch (Exception exception) {
                    System.out.println(exception.getMessage());

                }
            } catch (LocalisationSyntaxException syntaxException) {
                isGeneratedDataLabel.setText("Incorrect localisation given. Use syntax Country, City.");
                isGeneratedDataLabel.setStyle("-fx-border-color: red");
                System.out.println(syntaxException.getMessage());
                syntaxException.getStackTrace();
            } catch (PlaceExistingException existingException) {
                isGeneratedDataLabel.setText("Place which you type not exist. Try one more time.");
                isGeneratedDataLabel.setStyle("-fx-border-color: red");
                System.out.println(existingException.getMessage());
                existingException.getStackTrace();
            }
        } else {
            isGeneratedDataLabel.setText("Nothing Given. Type localisation which forecast you want to see");
            isGeneratedDataLabel.setStyle("-fx-border-color: #4f0808");
        }
    }

    //    TODO to consider changes
    public HashMap<Object[], Label> displayChosenData() throws NullPointerException {
        labelArray = new ArrayList<>();
        boxesArray = new ArrayList<>();
        HashMap<Object[], Label> objectsLabelMap;
        CheckBox[] boxes = {
                tempCheck, feelCheck, stampCheck, simpleCheck
        };
        if (dailyDay != null) {
            // TODO change to better name
            objectsLabelMap = new HashMap<>(Map.of(
                    new Object[]{dailyDay.getTemp(), tempCheck}, tempDataLabel,
                    new Object[]{dailyDay.getFeels_like(), feelCheck}, feelsDataLabel,
                    new Object[]{dailyDay, stampCheck, "stamps"}, stampsDataLabel,
                    new Object[]{dailyDay, simpleCheck, "simple"}, simpleDataLabel
            ));
        } else {
            for (CheckBox box : boxes) {
                box.setSelected(false);
            }
            throw new NullPointerException("Not properly generated data to invoke");
        }
        return objectsLabelMap;
    }


    public void givenCheckBoxChoice(ActionEvent event) {
        try {
            HashMap<Object[], Label> Choices = displayChosenData();
            for (Map.Entry<Object[], Label> choice : Choices.entrySet()) {
                try {
                    var keyObject = choice.getKey()[0];
                    CheckBox box = (CheckBox) choice.getKey()[1];
                    var valueLabel = choice.getValue();
                    if (box.isSelected()) {
                        if (keyObject instanceof Temp) {
                            valueLabel.setText(((Temp) keyObject).show("long"));
                        } else if (keyObject instanceof FeelsLike) {
                            valueLabel.setText(((FeelsLike) keyObject).show("long"));
                        } else if (keyObject instanceof Daily) {
                            String dailyText;
                            if (choice.getKey()[2].equals("stamps"))
                                dailyText = ((Daily) keyObject).show("long", "stamps");
                            else
                                dailyText = ((Daily) keyObject).show("long", "simple");
                            valueLabel.setText(dailyText);
                        }
                        labelArray.add(valueLabel);
                        boxesArray.add(box);

                    } else {
                        valueLabel.setText("");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (NullPointerException exception) {
            System.out.println(exception.getMessage());
            isGeneratedDataLabel.setText("Type localisation first ");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            countriesView.setVisible(false);
            citiesView.setVisible(false);
            String[] days = Arrays.stream(WeekDay.values())
                    .map(Enum::toString)
                    .toArray(String[]::new);
            dayListView.getItems().addAll(days); // adding days to view
            dayListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                    String chosenDay = dayListView.getSelectionModel().getSelectedItem();
                    WeekDay day = WeekDay.valueOf(chosenDay);
                    weekDay = day.ordinal();
                }
            });
            mainAnchorPane.setOnKeyPressed(event -> {
//                Menu handler ZOOM IN OUT RESET
                if (event.isControlDown() && (event.getCode() == KeyCode.O)) {
                    defaultFontSize -= 4;
                    event.consume();
                }
                if (event.isControlDown() && (event.getCode() == KeyCode.P)) {
                    defaultFontSize += 4;
                    event.consume();
                }
                if (event.isControlDown() && (event.getCode() == KeyCode.I)) {
                    defaultFontSize = 18;
                    event.consume();
                }
                mainAnchorPane.setStyle("-fx-font-size: " + defaultFontSize);
            });

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void goBackToOneDayForecastScene(ActionEvent event) throws IOException {
        isSecondSceneButtonClicked = false;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().
                getResource("/fxmlGUIbuilder/weather_application_scene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    public void goWeekForecastScene(ActionEvent event) throws IOException {
        isSecondSceneButtonClicked = true;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().
                getResource("/fxmlGUIbuilder/weather_week_forecast_Scene.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void postLocalisationToSecondScene(ActionEvent event) throws AppExceptions {
        int labelsColumn = 1;
        Map<GridPane, Integer> weekPanes =
                Map.of(
                        firstDayGridPane, 1,
                        secondDayGridPane, 2,
                        thirdDayGridPane, 3,
                        fourthDayGridPane, 4,
                        fifthDayGridPane, 5
                );
        try {
            int columnIndex = 0;
            ArrayList<Double> cords = getData.getApiCoordinates(secondSceneCordField.getText());
            try {
                mainWeather = getData.getApiValues(cords);
                for (Map.Entry<GridPane, Integer> pane : weekPanes.entrySet()) {
                    int rowIndex = 0;
                    if (mainWeather != null) {
                        if (!isGeneratedDataSecondScene.getText().isEmpty()) {
                            isGeneratedDataSecondScene.setText("");
                            isGeneratedDataSecondScene.setStyle("-fx-border-color: transparent");
                        }
                        dailyDay = mainWeather.getDaily().get(pane.getValue());
                        ArrayList<String> content = new ArrayList<>(
                                Arrays.asList(
                                        dailyDay.getTemp().show("short"),
                                        dailyDay.getFeels_like().show("short"),
                                        dailyDay.show("short", "stamps"),
                                        dailyDay.show("short", "simple")
                                ));
                        for (; rowIndex < firstDayGridPane.getRowCount(); rowIndex++, columnIndex++) {
                            if (isChangedSecondScene) {
                                Label tempLabel = secondSceneLabelArray.get(columnIndex);
                                tempLabel.setText(content.get(rowIndex));
                            } else {
                                Label tempLabel = new Label(content.get(rowIndex));
                                pane.getKey().add(tempLabel, labelsColumn, rowIndex);
                                secondSceneLabelArray.add(tempLabel);
                            }
                        }
                    }
                }
            } catch (Exception exception) {
                System.out.println("Weszło tutaj");
                isGeneratedDataSecondScene.setText("Wrong given Location. You have to type weather as: Country, City");
                isGeneratedDataSecondScene.setStyle("-fx-border-color: #aa2e2e");
                System.out.println(exception.getMessage());
            }
        } catch (LocalisationSyntaxException syntaxException) {
            isGeneratedDataSecondScene.setText("Incorrect syntax given. Use syntax Country, City.");
            isGeneratedDataSecondScene.setStyle("-fx-border-color: #4f0808");
            System.out.println(syntaxException.getMessage());
            syntaxException.getStackTrace();
        } catch (NotGivenInputException inputException) {
            isGeneratedDataSecondScene.setText("Nothing Given. Type localisation which forecast you want to see");
            isGeneratedDataSecondScene.setStyle("-fx-border-color: #4f0808");
            System.out.println(inputException.getMessage());
            inputException.getStackTrace();
        } catch (PlaceExistingException existingException) {
            isGeneratedDataSecondScene.setText("Incorrect localisation given. Try one more time.");
            isGeneratedDataSecondScene.setStyle("-fx-border-color: #4f0808");
            System.out.println(existingException.getMessage());
            existingException.getStackTrace();
        }
        isChangedSecondScene = true;
    }

    public void showLocalisationdHints(ActionEvent event) throws IOException {
        countriesView.setVisible(true);
        isHintButtonClicked = true;
    }

    public void getTextFieldHintOneDay(ActionEvent event) throws IOException {
        giveListOfCountries(cordField);
    }
    public void getTextFieldHintWeekScene(ActionEvent event) throws IOException {
        giveListOfCountries(secondSceneCordField);
    }

    public void giveListOfCountries(TextField sceneCordField) throws IOException {

        if (isHintButtonClicked) {
            ArrayList<String> countries = getData.getCountriesListFromJson();
            String backspaceCode = "\u0008";
            countriesView.getItems().addAll(countries);
            // countries list propety
            sceneCordField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (Objects.equals(newValue, backspaceCode)) {
                    countriesView.getItems().addAll(previousCuttedList);
                } else {
                    if (newValue != null) {
                        try {
                            ArrayList<String> cuttedList = getCountryRegexMatch(newValue, countries);
                            previousCuttedList = new ArrayList<>(countriesView.getItems());
                            ObservableList<String> items = countriesView.getItems();
                            for (int i = 0; i < items.size(); i++) {
                                if (!cuttedList.contains(items.get(i))) {
                                    items.remove(i);
                                    i--;
                                }
                            }
                            Set<String> uniqueCountries = new LinkedHashSet<>(countriesView.getItems());
                            countriesView.setItems(FXCollections.observableArrayList(uniqueCountries));
                        } catch (Exception exception) {
                        }
                    } else {
                        System.out.println("I am in false ");
                        countriesView.setVisible(false);
                    }

                }
            });

            TextField finalSceneCordField = sceneCordField;
            countriesView.setOnMouseClicked(item -> {
                countriesView.setVisible(false); // TODO after get cities make sure that return first ListView is possible
                String selectedCountryItem = countriesView.getSelectionModel().getSelectedItem();
                finalSceneCordField.setText(selectedCountryItem + ", ");
                finalSceneCordField.textProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        citiesView.setVisible(true);
                        try {
                            citiesView.getItems().setAll(getData.getCitiesBasedOnCountries(selectedCountryItem));
                        } catch (IOException e) {
                            System.out.println("Error in here " + e.getMessage());
                        }

                    } else {
                        System.out.println("I am in false ");
                        citiesView.setVisible(false);
                    }
                });
            });
            // cities list property
            citiesView.setOnMouseClicked(item -> {
                String selectedCityItem = citiesView.getSelectionModel().getSelectedItem();
                finalSceneCordField.setText(finalSceneCordField.getText() + selectedCityItem);
                citiesView.setVisible(false);
            });
        } else {
            countriesView.setVisible(false);
        }
    }

    public ArrayList<String> getCountryRegexMatch(String fieldValue, ArrayList<String> countries) {
        ArrayList<String> cuttedList = new ArrayList<>();
        for (String countIndex : countries) {
            if (countIndex.startsWith(fieldValue)) {
                cuttedList.add(countIndex);
            }
        }
        return cuttedList;
    }

    // ----------------------------------------------------------------------
    public void savingFile(ActionEvent event) {
        StringBuilder newDataToFile = getGeneratedDataToFile();
        if (newSelectedFile != null && !isFirstInit) {
            try {
                FileWriter appendWriter = new FileWriter(newSelectedFile, true);
                appendWriter.write(newDataToFile.toString());
                appendWriter.close();
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }

        } else { // isFirstInit and
//                System.out.println("First Save File ");
            savingFileAs(event);
        }
    }

    public void savingFileAs(ActionEvent event) {
        StringBuilder fileDatabuilder = getGeneratedDataToFile();
        try {
//            create file using FileChooser
            if (fileDatabuilder != null) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.setTitle("Create a new  file");
                fileChooser.getExtensionFilters().add(
                        new FileChooser.ExtensionFilter("Text Files", "*.txt"));

                newSelectedFile = fileChooser.showSaveDialog(null);
//            File selectedFile = fileChooser.showSaveDialog(null);
                if (newSelectedFile != null) {
                    try {

                        boolean createdFile = newSelectedFile.createNewFile();
                        if (createdFile) {
                            FileWriter writer = new FileWriter(newSelectedFile);
                            writer.write(fileDatabuilder.toString());
                            writer.close();
                        } else {
                            System.out.println("File aready exists");
                        }
                    } catch (IOException exception) {
                        System.out.println(exception.getMessage());
                    }

                } else {
                    System.out.println("File chooser canceled");
                }
            } else {
                System.out.println("To open FileChooser you have to generated data");
            }

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public StringBuilder getGeneratedDataToFile() {
        StringBuilder fileDatabuilder = new StringBuilder();
        if (isDataGenerated) { // true
            fileDatabuilder.append(dailyDay.getTemp().show("long")).
                    append(dailyDay.getFeels_like().show("long")).
                    append(dailyDay.show("long", "stamps")).
                    append(dailyDay.show("long", "simple"));


        } else if (isDataGeneratedSecondScene) {
            fileDatabuilder.append(dailyDay.getTemp().show("long")).
                    append(dailyDay.getFeels_like().show("long")).
                    append(dailyDay.show("long", "stamps")).
                    append(dailyDay.show("long", "simple"));

        } else {
            fileDatabuilder = null;
            isGeneratedDataLabel.setText("To save file you first have to generated data");
            isGeneratedDataLabel.setStyle("-fx-text-fill: rgb(255,160,122)");
        }
        return fileDatabuilder;
    }

    public void getAppExit(ActionEvent event) {
        AppMain mainWindow = new AppMain();
        mainWindow.exitRequest = true;
        mainWindow.exitWindow();
    }

    public void getAppShortcuts(ActionEvent event) {
        Stage shortCutsStage = new Stage();
        // TODO extend the view and shortcuts
        shortCutsStage.setTitle("Application Shortcuts");
        shortCutsStage.initModality(Modality.APPLICATION_MODAL);

        // Create the content for the about window
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);
        Label titleLabel = new Label("Shortcuts");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> shortCutsStage.close());
        content.getChildren().addAll(titleLabel,
                new Label("CTRL I: \t Restart Zoom View "),
                new Label("CTRL O: \t Zoom In           "),
                new Label("CTRL P: \t Zoom Out          "),
                new Label("ESC:    \t Exit from App     "),
                closeButton);

        // Set the scene and show the window
        Scene aboutScene = new Scene(content, 300, 200);
        shortCutsStage.setScene(aboutScene);
        shortCutsStage.showAndWait();
    }

    public void getAppRestart(ActionEvent event) {
        if (isDataChanged && boxesArray != null && labelArray != null && cordField != null) { // is false
            for (CheckBox box : boxesArray) {
                box.setSelected(false); // unselect all checkboxes and label data
            }
            for (Label label : labelArray) {
                label.setText("");
            }
            cordField.setText("");
        }
    }

    public void getRestartOfAppView(ActionEvent event) {
        defaultFontSize = 18;
        mainAnchorPane.setStyle("-fx-font-size: " + defaultFontSize);
    }

    public void getZoomWindowIn(ActionEvent event) {
        defaultFontSize += 4;
        mainAnchorPane.setStyle("-fx-font-size: " + defaultFontSize);
    }

    public void getZoomWindowOut(ActionEvent event) {
        defaultFontSize -= 4;
        mainAnchorPane.setStyle(defaultBackGround + "-fx-font-size: " + defaultFontSize);
    }

    public void getReportAppProblem(ActionEvent event) {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Send your opinion about Application");

        // Create the content for the dialog
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);
        Label label = new Label("Describe your problem in detail");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        TextField textField = new TextField();
        // TODO to implement style to report
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            // Get the input from the text field
            System.out.println("Problem with: " + textField.getText());
            // TODO to implement method that send an email on action
            // Close the dialog
            dialogStage.close();
        });
        content.getChildren().addAll(label, textField, okButton);

        // Set the scene and show the dialog
        Scene scene = new Scene(content, 300, 200);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    public void getAppHelpCenter(ActionEvent event) {
        try {
//            TODO make implementation of site that will represent help centre
            Desktop.getDesktop().browse(new URI("https://golo258.github.io/help_center/helpForecastCenter.html"));
        } catch (Exception exception) {
            System.out.println("Failed to open help center: " + exception.getMessage());
        }
    }

    public void getInfoAboutApp(ActionEvent event) {
        Stage aboutStage = new Stage();
        aboutStage.setTitle("About");
        aboutStage.initModality(Modality.APPLICATION_MODAL);

        // Create the content for the about window
        VBox content = new VBox();
        content.setAlignment(Pos.CENTER);
        content.setSpacing(10);
        Label titleLabel = new Label("Weather Application");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        Label versionLabel = new Label("Version 1.0");
        Label authorLabel = new Label("Author: Grzegorz Golonka");
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> aboutStage.close());
        content.getChildren().addAll(titleLabel, versionLabel, authorLabel, closeButton);

        // Set the scene and show the window
        Scene aboutScene = new Scene(content, 300, 200);
        aboutStage.setScene(aboutScene);
        aboutStage.showAndWait();

    }

// ---------------------------------------------------
}




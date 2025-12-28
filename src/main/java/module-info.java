module com.example.fxjavagui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.web;
    requires com.google.gson;
    requires org.json;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires java.net.http;
    requires java.desktop;

    opens com.application.main to com.fasterxml.jackson.databind;
    opens com.application.models to com.fasterxml.jackson.databind;
    exports com.application.controllers;
    exports com.application.main;
    opens com.application.controllers to javafx.fxml;
    exports com.application.exceptions;
    opens com.application.exceptions to javafx.fxml;

}
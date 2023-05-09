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

    opens com.application.Application to com.fasterxml.jackson.databind;
    opens com.application.MainClasses to com.fasterxml.jackson.databind;
    exports com.application.fxjavagui.Controllers;
    exports com.application.Application;
    opens com.application.fxjavagui.Controllers to javafx.fxml;
    exports com.application.fxjavagui.Controllers.Exceptions;
    opens com.application.fxjavagui.Controllers.Exceptions to javafx.fxml;

}
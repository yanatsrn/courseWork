package com.course.coursework;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Parent root= FXMLLoader.load(getClass().getResource("startPage.fxml"));
        stage.setTitle("Автосалон ДРАЙВ");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main() {
        launch();
    }
}
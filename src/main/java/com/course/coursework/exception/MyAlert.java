package com.course.coursework.exception;

import javafx.scene.control.Alert;

public class MyAlert {
    public static void createAlert(String title, String headerText, String mistake) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(mistake);
        alert.showAndWait();
    }
}

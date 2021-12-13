package com.course.coursework.controller;

import com.course.coursework.client.RunnableClient;
import com.course.coursework.exception.MyAlert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Label welcomeText;

    @FXML
    private Button startButton;

    @FXML
    private AnchorPane anchorRoot;

    @FXML
    private StackPane parentContainer;

    @FXML
    void pressExit(ActionEvent event) {
        System.exit(0);
    }


    @FXML
    protected void onStartButtonClick() throws Exception {
        welcomeText.setText("Приятной работы!");
            try {
                RunnableClient.command = "ENTER";

                startButton.getScene().getWindow().hide();
                Parent root= FXMLLoader.load(getClass().getResource("/com/course/coursework/authorization.fxml"));
                Stage stage=new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Авторизация");
                stage.show();
            } catch (IOException e) {
                MyAlert.createAlert("Ошибка","Произошла ошибка!",
                        "Не удалось выполнить операцию!\nПопробуйте повторить позже.");
            }

    }

}
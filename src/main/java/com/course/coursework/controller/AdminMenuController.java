package com.course.coursework.controller;

import com.course.coursework.entities.Person;
import com.course.coursework.entities.User;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class AdminMenuController {
    UserService userService = new UserServiceImpl();

    @FXML
    private Button goBackButton;

    @FXML
    private Button showCarsButton;

    @FXML
    private Button showUsersButton;

    @FXML
    private Button addModelButton;


    @FXML
    private void onShowUsersButtonClick() throws IOException {
        showCarsButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/userList.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Список пользователей");
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void onAddModelButtonClick() throws IOException {
        addModelButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/addModel.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Добавление модели");
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onGoBackButtonClick() throws IOException {
        goBackButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/authorization.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Авторизация");
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onShowCarsButtonClick() throws IOException {
        showCarsButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/table.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Таблица");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}

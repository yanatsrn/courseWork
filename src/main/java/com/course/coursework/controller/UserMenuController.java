package com.course.coursework.controller;

import com.course.coursework.client.RunnableClient;
import com.course.coursework.entities.Model;
import com.course.coursework.exception.MyAlert;
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

public class UserMenuController {

    UserService userService = new UserServiceImpl();
    static int id;
    @FXML
    private Button goBackButton;
    @FXML
    private TextField searchId;
    @FXML
    private Button searchCarsButton;
    @FXML
    private Button showCarsButton;
    @FXML
    private Button showProfileButton;
//    @FXML
//    private Button showHistoryButton;

    @FXML
    protected void onShowCarsButtonClick() throws IOException {
        RunnableClient.command = "EXIT";
        showCarsButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/userTable.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Таблица");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

//    @FXML
//    protected void onShowHistoryButtonClick() throws IOException {
//        showHistoryButton.getScene().getWindow().hide();
//        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/userHistory.fxml"));
//        Stage stage = new Stage();
//        stage.setTitle("Авторизация");
//        Scene scene = new Scene(parent);
//        stage.setScene(scene);
//        stage.show();
//
//    }

    @FXML
    protected void onGoBackButtonClick() throws IOException {
        goBackButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/authorization.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Авторизация");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void onShowProfileButtonClick() throws IOException {
        showProfileButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/showProfile.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Авторизация");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void searchModelById() throws IOException {
        id = Integer.parseInt(searchId.getText());
        try {
            List<Model> list = userService.showAllModels();
            if (id <= list.size() && id > 0) {
                searchCarsButton.getScene().getWindow().hide();
                Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/searchRecord.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Поиск");
                Scene scene = new Scene(parent);
                stage.setScene(scene);
                stage.show();
            }
            else
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность id");
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

}

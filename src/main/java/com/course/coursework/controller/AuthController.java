package com.course.coursework.controller;
import com.course.coursework.client.RunnableClient;
import com.course.coursework.entities.RoleType;
import com.course.coursework.entities.User;
import com.course.coursework.exception.MyAlert;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;
import com.course.coursework.validator.UserValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AuthController {
    UserService userService = new UserServiceImpl();
    List<User> users = new ArrayList<User>();

    @FXML
    private TextField login;
    @FXML
    private PasswordField password;
    @FXML
    private Button enterButton;
    @FXML
    private Label status;

    @FXML
    private Button registerButton;

    public static int userId;
    public static String userLogin;
    public static String userPassword;

    @FXML
    protected void onEnterButtonClick() {
        boolean isFound = false;
        try {
            if (UserValidator.isValidSignIn(login, password)) {
                boolean dataCorrect = true;
                if (!UserValidator.isValidLogin(login)){
                    MyAlert.createAlert("Ошибка","Произошла ошибка!","Проверьте корректность логина");
                    dataCorrect = false;
                }
                if (!UserValidator.isValidPassword(password)) {
                    MyAlert.createAlert("Ошибка","Произошла ошибка!","Проверьте корректность пароля");
                    dataCorrect = false;
                }
                if (dataCorrect) {
                    RunnableClient.command = "AUTHORIZATION";
                    users = userService.findAllUsers();
                    for (User list : users) {
                        if (list.getLogin().equals(login.getText()) && list.getPassword().equals(password.getText())){
                            isFound = true;
                            enterButton.getScene().getWindow().hide();
                            Parent parent;
                            if(list.getRole().toString().equalsIgnoreCase(RoleType.ADMIN.toString())) {
                                parent= FXMLLoader.load(getClass().getResource("/com/course/coursework/adminMenu.fxml"));
                            }
                            else {
                                userId = list.getUserId();
                                userLogin = login.getText();
                                userPassword = password.getText();
                                parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/userMenu.fxml"));
                            }
                            Stage stage=new Stage();
                            stage.setTitle("Меню");
                            Scene scene=new Scene(parent);
                            stage.setScene(scene);
                            stage.show();
                        }
                    }
                    if(!isFound)
                        MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Пароль не соответствует логину");
                }
            }
            else {
                MyAlert.createAlert("Ошибка","Произошла ошибка!","Заполните поля");
            }

        } catch (ServiceException | IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onRegisterButtonClick() {
        try {
            registerButton.getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/signUp.fxml"));
            Stage stage=new Stage();
            stage.setTitle("Меню админа");
            Scene scene=new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

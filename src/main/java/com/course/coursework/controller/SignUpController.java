package com.course.coursework.controller;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SignUpController {

    UserService userService = new UserServiceImpl();
    List<User> users = new ArrayList<User>();

    @FXML
    private TextField surname;

    @FXML
    private TextField name;

    @FXML
    private TextField age;

    @FXML
    private TextField phone;

    @FXML
    private TextField mail;

    @FXML
    private TextField login;

    @FXML
    private TextField password;

    @FXML
    private Button registerButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Label success;


    @FXML
    protected void onRegisterButtonClick() throws ServiceException, InterruptedException {

        boolean dataCorrect = true;
        if (UserValidator.createUser(surname, name, age, phone, mail, login, password)) {

            if(!UserValidator.isValidSurnameAndName(surname, name)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!","Проверьте корректность фамилии и имени");
                dataCorrect = false;
            }
            if (!UserValidator.isValidAge(age)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!","Проверьте корректность возраста");
                dataCorrect = false;
            }
            if (!UserValidator.isValidPhone(phone)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!","Проверьте корректность телефона");
                dataCorrect = false;
            }
            if (!UserValidator.isValidMail(mail)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!","Проверьте корректность почты");
                dataCorrect = false;
            }
            if (!UserValidator.isValidLogin(login)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!","Проверьте корректность логина");
                dataCorrect = false;
            }
            if (!UserValidator.isValidPassword(password)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!","Проверьте корректность пароля");
                dataCorrect = false;
            }

            if (dataCorrect) {
                boolean isExist = userService.isExistMail(mail.getText());
                if (isExist) {
                    MyAlert.createAlert("Ошибка", "Произошла ошибка!","Такая почта уже существует");
                    dataCorrect = false;
                }
            }

            if (dataCorrect) {
                users = userService.findAllUsers();
                User newUser = new User();
                newUser.setSurname(surname.getText());
                newUser.setName(name.getText());
                newUser.setAge(Integer.parseInt(age.getText()));
                newUser.setPhone(phone.getText());
                newUser.setMail(mail.getText());
                newUser.setLogin(login.getText());
                newUser.setPassword(password.getText());
                newUser.setPersonId(users.toArray().length + 1);
                newUser.setRole(RoleType.USER);
                userService.createUser(newUser);

                success.setText("Пользователь зарегистрирован!");
            }
        }
        else {
            MyAlert.createAlert("Ошибка", "Произошла ошибка!","Заполните поля!");
        }
    }

    @FXML
    protected void onGoBackButtonClick() {

        try {
            goBackButton.getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/authorization.fxml"));
            Stage stage=new Stage();
            stage.setTitle("Авторизация");
            Scene scene=new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

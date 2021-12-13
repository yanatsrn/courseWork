package com.course.coursework.controller;

import com.course.coursework.entities.Model;
import com.course.coursework.entities.User;
import com.course.coursework.exception.MyAlert;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;
import com.course.coursework.validator.UserValidator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowProfileController implements Initializable {
    UserService userService = new UserServiceImpl();
    User entity = null;

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
    private TextField discountSum;

    @FXML
    private Button goBackButton;
    @FXML
    private Button updateProfile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            entity = userService.findUser(AuthController.userLogin);
            System.out.println(entity);
            surname.setText(entity.getSurname());
            name.setText(entity.getName());
            age.setText(String.valueOf(entity.getAge()));
            phone.setText(entity.getPhone());
            mail.setText(entity.getMail());
            login.setText(entity.getLogin());
            password.setText(entity.getPassword());
            discountSum.setText(String.valueOf(entity.getDiscountSum()));

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onGoBackButtonClick() throws IOException {
        goBackButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/userMenu.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Меню пользователя");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onUpdateProfileButtonClick() {
        boolean dataCorrect = true;
        if (UserValidator.createUser(surname, name, age, phone, mail, login, password)) {
            if (!UserValidator.isValidSurnameAndName(surname, name)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность фамилии и имени");
                dataCorrect = false;
            }
            if (!UserValidator.isValidAge(age)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность возраста");
                dataCorrect = false;
            }
            if (!UserValidator.isValidPhone(phone)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность телефона");
                dataCorrect = false;
            }
            if (!UserValidator.isValidPassword(password)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность пароля");
                dataCorrect = false;
            }
            if (dataCorrect) {
                entity.setSurname(surname.getText());
                entity.setName(name.getText());
                entity.setAge(Integer.parseInt(age.getText()));
                entity.setPhone(phone.getText());
                entity.setPassword(password.getText());
                try {
                    if (userService.updateUser(entity))
                        MyAlert.createAlert("Ошибка", "Произошло действие!", "Запись изменена!");
                    else
                        MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Запись не изменена!");
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            }
        }
        else
            MyAlert.createAlert("Ошибка","Произошла ошибка!","Заполните поля!");
    }
}

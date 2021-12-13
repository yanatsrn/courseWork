package com.course.coursework.controller;

import com.course.coursework.entities.Car;
import com.course.coursework.entities.Facade;
import com.course.coursework.entities.Model;
import com.course.coursework.entities.Type;
import com.course.coursework.exception.MyAlert;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;
import com.course.coursework.validator.UserValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.BaseNumberUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddModelController implements Initializable {

    ObservableList<Car> carList = FXCollections.observableArrayList();
    ObservableList<Type> typeList = FXCollections.observableArrayList();
    UserService users = new UserServiceImpl();

    @FXML
    ComboBox<Car> car;

    @FXML
    ComboBox<Type> type;

    @FXML
    private Button goBackButton;

    @FXML
    private TextField name;
    @FXML
    private TextField year;
    @FXML
    private TextField distance;
    @FXML
    private TextField fuel;
    @FXML
    private TextField fuelConsumption;
    @FXML
    private TextField transmission;
    @FXML
    private TextField price;

    @FXML
    protected void onAddButtonClick() throws ServiceException {

        boolean dataCorrect = true;

        if (UserValidator.createModel(name, year, fuel, fuelConsumption, price)) {
            if (!UserValidator.isValidString(fuel)) {
                dataCorrect = false;
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность топлива!");
            }
            if (!UserValidator.isValidNumber(year)) {
                dataCorrect = false;
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность года выпуска!");
            }
            if (!UserValidator.isValidFuelConsumption(fuelConsumption)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность расхода топлива!");
                dataCorrect = false;
            }
            if (!UserValidator.isValidNumber(price)) {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность расхода топлива!");
                dataCorrect = false;
            }
        }
        else {
            MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Заполните все поля!");
            dataCorrect = false;
        }
        //todo Uservalidator для необязательных параетров и по такой же схее
        if (dataCorrect) {
            Model model = null;
            if (distance.getText() != null && !distance.getText().isEmpty()) {
                if (StringUtils.isNumeric(distance.getText())) {
                    if (transmission.getText() != null && transmission.getText().equals("Автомат")) {
                        model = new Model.Builder(name.getText(), year.getText(), fuel.getText(),
                                fuelConsumption.getText(), Integer.parseInt(price.getText()))
                                .distance(Integer.parseInt(distance.getText()))
                                .transmission(transmission.getText())
                                .carId(car.getValue().getCarId())
                                .typeId(type.getValue().getTypeId())
                                .build();
                    } else {
                        model = new Model.Builder(name.getText(), year.getText(), fuel.getText(),
                                fuelConsumption.getText(), Integer.parseInt(price.getText()))
                                .distance(Integer.parseInt(distance.getText()))
                                .carId(car.getValue().getCarId())
                                .typeId(type.getValue().getTypeId())
                                .build();
                    }
                }
            }
            else if (transmission.getText() != null && transmission.getText().equals("Автомат")) {
                model = new Model.Builder(name.getText(), year.getText(), fuel.getText(),
                        fuelConsumption.getText(), Integer.parseInt(price.getText()))
                        .transmission(transmission.getText())
                        .carId(car.getValue().getCarId())
                        .typeId(type.getValue().getTypeId())
                        .build();
            }
            else {
                model = new Model.Builder(name.getText(), year.getText(), fuel.getText(),
                        fuelConsumption.getText(), Integer.parseInt(price.getText()))
                        .carId(car.getValue().getCarId())
                        .typeId(type.getValue().getTypeId())
                        .build();
            }

            if (users.createModel(model))
                MyAlert.createAlert("Уведомление", "Произошло действие!", "Модель добавлена!");
            else
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Модель не добавлена!");
        }
    }

    @FXML
    protected void onGoBackButtonClick() throws IOException {
        goBackButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/adminMenu.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Меню администратора");
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Facade facade = new Facade();

            for(Car entity : facade.getCarsList()) {
                carList.add(entity);
            }
            car.setItems(carList);
            car.setValue(carList.get(0));
            for (Type entity : facade.getTypesList()) {
                typeList.add(entity);
            }
            type.setItems(typeList);
            type.setValue(typeList.get(0));

        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }
}

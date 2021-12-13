package com.course.coursework.controller;

import com.course.coursework.entities.Car;
import com.course.coursework.entities.Model;
import com.course.coursework.entities.Type;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FullTableController implements Initializable {
    UserService userService = new UserServiceImpl();

    @FXML
    private TextField company;
    @FXML
    private TextField country;
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
    private TextField isBought;
    @FXML
    private TextField body;
    @FXML
    private TextField salon;
    @FXML
    private TextField color;
    @FXML
    private TextField parking;
    @FXML
    private Button goBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Model entity = TableController.modelFillRecordSelected;
            System.out.println(entity);
            int entityId = entity.getModelId();
            List<Car> car;
            List<Model> model;
            List<Type> type;
            model = userService.showOneModel(entityId);
            car = userService.showOneCar(model.get(0).getCarId());
            type = userService.showOneType(model.get(0).getTypeId());
            company.setText(car.get(0).getName());
            country.setText(car.get(0).getCountry());
            name.setText(model.get(0).getName());
            year.setText(model.get(0).getYear());
            distance.setText(String.valueOf(model.get(0).getDistance()));
            fuel.setText(model.get(0).getFuel());
            fuelConsumption.setText(model.get(0).getFuelConsumption());
            transmission.setText(model.get(0).getTransmission());
            price.setText(String.valueOf(model.get(0).getPrice()));
            if (model.get(0).isBought())
                isBought.setText("Да");
            else
                isBought.setText("Нет");
            body.setText(type.get(0).getBody());
            salon.setText(type.get(0).getSalon());
            color.setText(type.get(0).getColor());
            if (type.get(0).isParkingHelper())
                parking.setText("Да");
            else
                parking.setText("Нет");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onGoBackButtonClick() throws IOException {
        goBackButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/table.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Таблица");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}

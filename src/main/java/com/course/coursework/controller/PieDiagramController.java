package com.course.coursework.controller;

import com.course.coursework.entities.Car;
import com.course.coursework.entities.Model;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PieDiagramController implements Initializable {

    UserService userService = new UserServiceImpl();

    @FXML
    private PieChart pie;

    @FXML
    private Button goBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int count = 0;
        try {
            List<Model> models;
            List<Car> carsName = userService.showAllCars();
            for(Car car : carsName){
                models = userService.countCar(car.getCarId());
                count = models.size();
                PieChart.Data slice1 = new PieChart.Data(car.getName(), count);
                pie.getData().add(slice1);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void onGoBackButtonClick() throws IOException {
        goBackButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/userTable.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Меню администратора");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }
}

package com.course.coursework.controller;

import com.course.coursework.entities.Model;
import com.course.coursework.exception.MyAlert;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class UpdateRecordController implements Initializable {

    ObservableList<String> transmissionObservableList = FXCollections.observableArrayList("Автомат", "Механика");
    UserService userService = new UserServiceImpl();
    int entityId;

    @FXML
    private TextField updateName;

    @FXML
    private TextField updateYear;

    @FXML
    private TextField updateDistance;

    @FXML
    private TextField updateFuel;

    @FXML
    private TextField updateFuelConsumption;

    @FXML
    private ComboBox<String> updateTransmission;

    @FXML
    private TextField updatePrice;

    @FXML
    private Button goBackButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Model entity = TableController.modelUpdateRecordSelected;
        System.out.println(entity);
        entityId = entity.getModelId();
        updateName.setText(entity.getName());
        updateYear.setText(entity.getYear());
        updateDistance.setText(String.valueOf(entity.getDistance()));
        updateFuel.setText(entity.getFuel());
        updateFuelConsumption.setText(entity.getFuelConsumption());
        updateTransmission.setItems(transmissionObservableList);
        updateTransmission.setValue(entity.getTransmission());
        updatePrice.setText(String.valueOf(entity.getPrice()));
    }

    @FXML
    protected void onGoBackButtonClick() throws IOException {
        goBackButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/table.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Таблица");
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onUpdateButtonClick() {
        Model model = new Model();
        model.setModelId(entityId);
        model.setName(updateName.getText());
        model.setYear(updateYear.getText());
        model.setDistance(Integer.parseInt(updateDistance.getText()));
        model.setFuel(updateFuel.getText());
        model.setFuelConsumption(updateFuelConsumption.getText());
        model.setTransmission(updateTransmission.getValue());
        model.setPrice(Integer.parseInt(updatePrice.getText()));
        try {
            boolean isUpdated;
            isUpdated = userService.updateModel(model);
            if (isUpdated)
                MyAlert.createAlert("Уведомление", "Произошло действие!", "Запись отредактирована!");
            else
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Запись не отредактирована!");
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

}

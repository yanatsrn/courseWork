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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import java.util.*;

public class TableController implements Initializable {
    ObservableList<String> columnObservableList = FXCollections.observableArrayList("ID", "Название", "Год",
            "Пробег", "Топливо", "Расход", "Коробка передач", "Цена");
    ObservableList<Model> modelObservableList = FXCollections.observableArrayList();
    UserService userService = new UserServiceImpl();
    static Model modelFillRecordSelected = new Model();
    static Model modelUpdateRecordSelected = new Model();

    public Model getModelFillRecordSelected() {
        return modelFillRecordSelected;
    }

    @FXML
    private Button goBackButton;

    @FXML
    private Button getFullRecordButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button pieButton;

    @FXML
    private TableView<Model> modelsTableView;

    @FXML
    private TableColumn<Model, Integer> idColumn;

    @FXML
    private TableColumn<Model, String> modelNameColumn;

    @FXML
    private TableColumn<Model, String> yearColumn;

    @FXML
    private TableColumn<Model, Integer> distanceColumn;

    @FXML
    private TableColumn<Model, String> fuelColumn;

    @FXML
    private TableColumn<Model, String> fuelConsumptionColumn;

    @FXML
    private TableColumn<Model, String> transmissionColumn;

    @FXML
    private TableColumn<Model, Integer> priceColumn;

    @FXML
    ComboBox<String> sort;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sort.setItems(columnObservableList);
        sort.setValue("ID");
        initializeDataInTable();
        idColumn.setCellValueFactory(new PropertyValueFactory<>("modelId"));
        modelNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        distanceColumn.setCellValueFactory(new PropertyValueFactory<>("distance"));
        fuelColumn.setCellValueFactory(new PropertyValueFactory<>("fuel"));
        fuelConsumptionColumn.setCellValueFactory(new PropertyValueFactory<>("fuelConsumption"));
        transmissionColumn.setCellValueFactory(new PropertyValueFactory<>("transmission"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        modelsTableView.setItems(modelObservableList);
    }

    @FXML
    protected void onGetFullRecordButtonClick() throws IOException {
        boolean isCorrect = false;
        modelFillRecordSelected = modelsTableView.getSelectionModel().getSelectedItem();
        if(modelFillRecordSelected != null) {
            isCorrect = true;
        }
        if (isCorrect) {
            System.out.println(modelFillRecordSelected);
            getFullRecordButton.getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/fullTable.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Подробная таблица");
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        else
            MyAlert.createAlert("Ошибка","Произошла ошибка!","Не выбрана строка");
    }

    @FXML
    protected void onUpdateButtonClick() throws IOException {
        boolean isCorrect = false;
        modelUpdateRecordSelected = modelsTableView.getSelectionModel().getSelectedItem();
        if(modelFillRecordSelected != null) {
            isCorrect = true;
        }
        if (isCorrect) {
            System.out.println(modelUpdateRecordSelected);
            updateButton.getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/updateRecord.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Изменение");
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        else
            MyAlert.createAlert("Ошибка","Произошла ошибка!","Не выбрана строка");
    }

    @FXML
    protected void onDeleteButtonClick() {
        boolean isCorrect = false;
        modelFillRecordSelected = modelsTableView.getSelectionModel().getSelectedItem();
        if(modelFillRecordSelected != null) {
            isCorrect = true;
        }
        if (isCorrect) {
            System.out.println(modelFillRecordSelected);
            int id = modelFillRecordSelected.getModelId();
            try {
                boolean isDeleted = userService.deleteModel(id);
                if (isDeleted) {
                    MyAlert.createAlert("Уведомление", "Произошло действие!", "Запись удалена!");
                    modelObservableList.clear();
                    initializeDataInTable();
                } else
                    MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Запись не удалена!");
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }
        else
            MyAlert.createAlert("Ошибка","Произошла ошибка!","Не выбрана строка");
    }

    @FXML
    protected void onGoBackButtonClick() throws IOException {
        goBackButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/adminMenu.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Меню администратора");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onSortButtonClick() {
        try {
            modelObservableList.clear();
            List<Model> models = userService.sortByOneParameter(sort.getValue());
            for (Model entity : models) {
                if (!entity.isBought()) {
                    modelObservableList.add(new Model(
                            entity.getModelId(),
                            entity.getName(),
                            entity.getYear(),
                            entity.getDistance(),
                            entity.getFuel(),
                            entity.getFuelConsumption(),
                            entity.getTransmission(),
                            entity.getPrice()
                    ));
                    modelsTableView.setItems(modelObservableList);
                }
            }
            System.out.println(modelObservableList);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    protected void initializeDataInTable() {
        try {
            List<Model> models = new ArrayList<>();
            models = userService.showAllModels();
            for (Model entity : models) {
                if (!entity.isBought()) {
                    modelObservableList.add(new Model(
                            entity.getModelId(),
                            entity.getName(),
                            entity.getYear(),
                            entity.getDistance(),
                            entity.getFuel(),
                            entity.getFuelConsumption(),
                            entity.getTransmission(),
                            entity.getPrice()
                    ));
                    modelsTableView.setItems(modelObservableList);
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }


}

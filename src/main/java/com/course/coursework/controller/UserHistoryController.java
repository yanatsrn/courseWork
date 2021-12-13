package com.course.coursework.controller;

import com.course.coursework.entities.Model;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javax.xml.transform.Result;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserHistoryController implements Initializable {

    ObservableList<ResultSet> observableList = FXCollections.observableArrayList();
    UserService userService = new UserServiceImpl();

    static int id;

    @FXML
    private TableView<ResultSet> table;

    @FXML
    private TableColumn<ResultSet, String> modelName;

    @FXML
    private TableColumn<Model, Integer> price;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDataInTable();
        modelName.setCellValueFactory(new PropertyValueFactory<>("modelName"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        table.setItems(observableList);
    }

    protected void initializeDataInTable() {
        try {
            id = AuthController.userId;
            ResultSet resultSet = userService.showUserHistory(id);
            while (resultSet.next()) {
                observableList.add(resultSet);
            }
            table.setItems(observableList);
        } catch (ServiceException | SQLException e) {
            e.printStackTrace();
        }
    }
}

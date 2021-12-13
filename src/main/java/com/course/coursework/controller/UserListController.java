package com.course.coursework.controller;

import com.course.coursework.entities.Model;
import com.course.coursework.entities.User;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserListController implements Initializable {

    ObservableList<User> usersObservableList = FXCollections.observableArrayList();
    UserService userService = new UserServiceImpl();

    @FXML
    private TableView<User> table;

    @FXML
    private TableColumn<User, Integer> userId;
    @FXML
    private TableColumn<User, String> surname;
    @FXML
    private TableColumn<User, String> name;
    @FXML
    private TableColumn<User, Integer> age;
    @FXML
    private TableColumn<User, String> phone;
    @FXML
    private TableColumn<User, String> mail;
    @FXML
    private TableColumn<User, String> login;
    @FXML
    private TableColumn<User, Integer> discountSum;

    @FXML
    private Button goBackButton;

    @FXML
    private Button excelButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeDataInTable();
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        discountSum.setCellValueFactory(new PropertyValueFactory<>("discountSum"));
        table.setItems(usersObservableList);
    }

    @FXML
    protected void onExcelButtonClick() {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выберите путь");
        File file=fileChooser.showSaveDialog(new Stage());
        String fileName= file.getAbsolutePath();
        XSSFWorkbook workbook = new XSSFWorkbook(XSSFWorkbookType.XLSX);

        Sheet sheet = workbook.createSheet("User");
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)14);
        headerStyle.setFont(font);

        //Header cell
        ObservableList<TableColumn<User, ?>> columns = table.getColumns();
        int count = 0;
        for(TableColumn<User, ?> column : columns){
            Cell headerCell = header.createCell(count++);
            headerCell.setCellValue(column.getText());
            headerCell.setCellStyle(headerStyle);
        }

        //Next cell (tableViewClient.getItems())
        for (int i = 0; i < table.getItems().size(); i++) {
            Row row=sheet.createRow(i+1);
            Cell id = row.createCell(0);
            Cell surname = row.createCell(1);
            Cell name = row.createCell(2);
            Cell age = row.createCell(3);
            Cell phone = row.createCell(4);
            Cell mail = row.createCell(5);
            Cell login = row.createCell(6);
            Cell discountSum = row.createCell(7);
            id.setCellValue(table.getItems().get(i).getUserId());
            surname.setCellValue(table.getItems().get(i).getSurname());
            name.setCellValue(table.getItems().get(i).getName());
            age.setCellValue(table.getItems().get(i).getAge());
            phone.setCellValue(table.getItems().get(i).getPhone());
            mail.setCellValue(table.getItems().get(i).getMail());
            login.setCellValue(table.getItems().get(i).getLogin());
            discountSum.setCellValue(table.getItems().get(i).getDiscountSum());
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            MyAlert.createAlert("Уведомление", "Произошло действие", "Отчет успешно сохранен");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    protected void initializeDataInTable() {
        try {
            List<User> users =userService.showAllUsers();
            for (User entity : users) {
                usersObservableList.add(new User(
                     entity.getSurname(),
                     entity.getName(),
                     entity.getAge(),
                     entity.getPhone(),
                     entity.getMail(),
                     entity.getUserId(),
                     entity.getLogin(),
                     entity.getDiscountSum()
                ));
                table.setItems(usersObservableList);
            }
        } catch (ServiceException e) {
            e.printStackTrace();
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
}

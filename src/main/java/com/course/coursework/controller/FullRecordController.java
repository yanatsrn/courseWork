package com.course.coursework.controller;

import com.course.coursework.entities.*;
import com.course.coursework.exception.MyAlert;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;
import com.course.coursework.validator.UserValidator;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class FullRecordController implements Initializable {
    ObservableList<Object> observableList = FXCollections.observableArrayList();
    UserService userService = new UserServiceImpl();
    static int selectedModelId;
    static int selectedUserId;
    static int selectedModelPrice;

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
    @FXML
    private Button buyCarButton;
    @FXML
    private Button confirmButton;
    @FXML
    private TextField login;
    @FXML
    private TextField password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Model entity = UserTableController.modelFillRecordSelected;
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
    protected void onBuyCarButtonClick() {
        buyCarButton.setVisible(false);
        login.setVisible(true);
        password.setVisible(true);
        confirmButton.setVisible(true);
    }

    @FXML
    protected void onConfirmButtonClick() throws IOException {
        boolean status = false;
        List<User> users;
        try {
            if (UserValidator.isValidSignIn(login, password)) {
                boolean dataCorrect = true;
                if (!UserValidator.isValidLogin(login)) {
                    MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность логина");
                    dataCorrect = false;
                }
                if (!UserValidator.isValidPassword(password)) {
                    MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Проверьте корректность пароля");
                    dataCorrect = false;
                }
                if (dataCorrect) {
                    users = userService.findAllUsers();
                    for (User list : users) {
                        if (list.getLogin().equals(login.getText()) && list.getPassword().equals(password.getText())) {
                            status = true;
                            selectedModelId = UserTableController.modelFillRecordSelected.getModelId();
                            selectedUserId = AuthController.userId;
                            selectedModelPrice = UserTableController.modelFillRecordSelected.getPrice() - list.getDiscountSum();
                            int newDiscountSum = selectedModelPrice / 100;
                            boolean newIsBought = true;
                            if(userService.addPurchase(selectedModelId, selectedUserId, selectedModelPrice)) {
                                if(userService.updateAfterPurchase(selectedUserId, newDiscountSum, selectedModelId, newIsBought)) {
                                    getCheck();
                                    MyAlert.createAlert("Уведомление", "Произошло действие!", "Покупка оформлена!");
                                    buyCarButton.getScene().getWindow().hide();
                                    Parent parent;
                                    parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/userMenu.fxml"));
                                    Stage stage = new Stage();
                                    stage.setTitle("Меню");
                                    Scene scene = new Scene(parent);
                                    stage.setScene(scene);
                                    stage.show();
                                }
                                else
                                    MyAlert.createAlert("Уведомление", "Произошла ошибка!", "Данные машины и пользователя не изменены!");

                            }
                            else
                                MyAlert.createAlert("Уведомление", "Произошла ошибка!", "Покупка не оформлена!");

                        }


                    }
                    if (!status)
                        MyAlert.createAlert("Уведомление", "Произошла ошибка!", "Логин не соответсвует паролю!");

                }

            } else {
                MyAlert.createAlert("Ошибка", "Произошла ошибка!", "Заполните поля");
            }
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void getCheck() {
        try {
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            String currentDateTime = dateFormatter.format(new Date());
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(currentDateTime + "Чек.pdf"));
            document.open();
            Paragraph header = new Paragraph();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Check for " + login.getText(), font);
            Font textFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk textChunk = new Chunk("Model:", textFont);
            header.add(chunk);
            document.add(header);
            document.add(textChunk);
            Paragraph paragraph = new Paragraph();
            PdfPTable table = new PdfPTable(3);
            addTableHeader(table);
            addRows(table);
            paragraph.add(table);
            document.add(paragraph);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void addTableHeader(PdfPTable table) {
        Stream.of("Company", "Model", "Price")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
        table.addCell(company.getText());
        table.addCell(name.getText());
        table.addCell(price.getText());
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

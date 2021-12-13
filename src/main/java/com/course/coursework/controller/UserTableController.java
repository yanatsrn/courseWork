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
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UserTableController implements Initializable {
    ObservableList<String> columnObservableList = FXCollections.observableArrayList("ID", "Название", "Год",
            "Пробег", "Топливо", "Расход", "Коробка передач", "Цена");
    ObservableList<Model> modelObservableList = FXCollections.observableArrayList();
    UserService userService = new UserServiceImpl();
    static Model modelFillRecordSelected = new Model();
    @FXML
    private Button goBackButton;
    @FXML
    private Button pieButton;

    @FXML
    private Button getFullRecordButton;

    @FXML
    private Button updateButton;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    @FXML
    protected void onPieButtonClick() throws IOException {
        pieButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/pieDiagram.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Круговая диаграмма");
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void onGoBackButtonClick() throws IOException {

        goBackButton.getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/userMenu.fxml"));
        Stage stage=new Stage();
        stage.setTitle("Меню");
        Scene scene=new Scene(parent);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void onGraficButtonClick() throws IOException {
//        graficButton.getScene().getWindow().hide();
        try {
            List<Model> golf = userService.findGolf();
            if (golf.size() > 2) {
                NumberAxis year = new NumberAxis(2010, 2021, 1);
                NumberAxis price = new NumberAxis(5000, 25000, 5000);
                LineChart grafic = new LineChart(year, price);
                XYChart.Series series = new XYChart.Series();
                for (Model entity : golf) {
                    series.getData().add(new XYChart.Data(Integer.parseInt(entity.getYear()), entity.getPrice()));
                }
                //Setting the data to Line chart
                grafic.getData().add(series);

                //Creating a Group object
                Group root = new Group(grafic);

                //Creating a scene object
                Scene scene = new Scene(root, 600, 400);

                Stage stage = new Stage();

                //Setting title to the Stage
                stage.setTitle("График роста цен на модель Golf");

                //Adding scene to the stage
                stage.setScene(scene);

                //Displaying the contents of the stage
                stage.show();

            }
        } catch (ServiceException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onExcelButtonClick() {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Выберите путь");
        File file=fileChooser.showSaveDialog(new Stage());
        String fileName= file.getAbsolutePath();
        XSSFWorkbook workbook = new XSSFWorkbook(XSSFWorkbookType.XLSX);

        Sheet sheet = workbook.createSheet("Model");
        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short)14);
        headerStyle.setFont(font);

        //Header cell
        ObservableList<TableColumn<Model, ?>> columns = modelsTableView.getColumns();
        int count = 0;
        for(TableColumn<Model, ?> column : columns){
            Cell headerCell = header.createCell(count++);
            headerCell.setCellValue(column.getText());
            headerCell.setCellStyle(headerStyle);
        }

        //Next cell (tableViewClient.getItems())
        for (int i = 0; i < modelsTableView.getItems().size(); i++) {
            Row row=sheet.createRow(i+1);
            Cell id = row.createCell(0);
            Cell name = row.createCell(1);
            Cell year = row.createCell(2);
            Cell distance = row.createCell(3);
            Cell fuel = row.createCell(4);
            Cell fuelConsumption = row.createCell(5);
            Cell transmission = row.createCell(6);
            Cell price = row.createCell(7);
            id.setCellValue(modelsTableView.getItems().get(i).getModelId());
            name.setCellValue(modelsTableView.getItems().get(i).getName());
            year.setCellValue(modelsTableView.getItems().get(i).getYear());
            distance.setCellValue(modelsTableView.getItems().get(i).getDistance());
            fuel.setCellValue(modelsTableView.getItems().get(i).getFuel());
            fuelConsumption.setCellValue(modelsTableView.getItems().get(i).getFuelConsumption());
            transmission.setCellValue(modelsTableView.getItems().get(i).getTransmission());
            price.setCellValue(modelsTableView.getItems().get(i).getPrice());
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

    @FXML
    protected void onGetFullRecordButtonClick() throws IOException {
        boolean isCorrect = false;
        modelFillRecordSelected = modelsTableView.getSelectionModel().getSelectedItem();
        if(modelFillRecordSelected != null) {
            isCorrect = true;
        }
        if (isCorrect) {
            getFullRecordButton.getScene().getWindow().hide();
            Parent parent = FXMLLoader.load(getClass().getResource("/com/course/coursework/fullRecord.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Полная запись");
            Scene scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        }
        else
            MyAlert.createAlert("Ошибка","Произошла ошибка!","Не выбрана строка");
    }

}

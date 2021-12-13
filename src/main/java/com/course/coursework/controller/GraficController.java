package com.course.coursework.controller;

import com.course.coursework.entities.Model;
import com.course.coursework.entities.User;
import com.course.coursework.exception.ServiceException;
import com.course.coursework.service.UserService;
import com.course.coursework.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class GraficController implements Initializable {
    UserService userService = new UserServiceImpl();

    @FXML
    private Button button;
//    @FXML
//    private LineChart grafic;
//    @FXML
//    private NumberAxis year;
//    @FXML
//    private NumberAxis price;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Defining the x axis
        NumberAxis xAxis = new NumberAxis(1960, 2020, 10);
        xAxis.setLabel("Years");

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis   (0, 350, 50);
        yAxis.setLabel("No.of schools");

        //Creating the line chart
        LineChart linechart = new LineChart(xAxis, yAxis);


        //Prepare XYChart.Series objects by setting data
        XYChart.Series series = new XYChart.Series();
        series.setName("No of schools in an year");

        series.getData().add(new XYChart.Data(1970, 15));
        series.getData().add(new XYChart.Data(1980, 30));
        series.getData().add(new XYChart.Data(1990, 60));
        series.getData().add(new XYChart.Data(2000, 120));
        series.getData().add(new XYChart.Data(2013, 240));
        series.getData().add(new XYChart.Data(2014, 300));

        //Setting the data to Line chart
        linechart.getData().add(series);

        //Creating a Group object
        Group root = new Group(linechart);
//
//        //Creating a scene object
//        Scene scene = new Scene(root, 600, 400);
//
//        Stage stage = new Stage();
//
//        //Setting title to the Stage
//        stage.setTitle("Line Chart");
//
//        //Adding scene to the stage
//        stage.setScene(scene);
//
//        //Displaying the contents of the stage
//        stage.show();
//        try {
//            List<Model> golf = userService.findGolf();
//            if (golf.size() > 2) {
//                NumberAxis year = new NumberAxis(2010, 2021, 1);
//                NumberAxis price = new NumberAxis(5000, 25000, 5000);
//                LineChart grafic = new LineChart(price, year);
//                XYChart.Series series = new XYChart.Series();
//                for (Model entity : golf) {
//                    series.getData().add(new XYChart.Data(entity.getPrice(), entity.getYear()));
//                }
//                //Setting the data to Line chart
//                grafic.getData().add(series);
//
//            }
//        } catch (ServiceException e) {
//            e.printStackTrace();
//        }

    }
}

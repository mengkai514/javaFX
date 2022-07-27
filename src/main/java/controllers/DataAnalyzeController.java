package controllers;

import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.*;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.ResourceBundle;

public class DataAnalyzeController implements Initializable {
    @FXML
    private StackedBarChart stackedBarChart;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CategoryAxis  xAxis = (CategoryAxis) stackedBarChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) stackedBarChart.getYAxis();
        //轴名

        xAxis.setLabel("月份");
        yAxis.setLabel("产品样貌数量");
        Calendar c = Calendar.getInstance();
        int month = c.get(Calendar.MONTH)+1;
        String[] numberMonth = new String[month + 1];
        int[] PinGlueNumber = new int[month + 1];
        int[] PinAsknewNumber = new int[month + 1];
        int[] standardNumber = new int[month + 1];
        int[] GlueOutNumber = new int[month + 1];

        for(int i = 1; i < PinGlueNumber.length; i++){
            PinGlueNumber[i] = (int)(Math.random()*100+1);
        }
        for(int i = 1; i < PinAsknewNumber.length; i++){
            PinAsknewNumber[i] = (int)(Math.random()*100+1);
        }
        for(int i = 1; i < standardNumber.length; i++){
            standardNumber[i] = (int)(Math.random()*100+1);
        }
        for(int i = 1; i < GlueOutNumber.length; i++){
            GlueOutNumber[i] = (int)(Math.random()*100+1);
        }

        ArrayList<String> list = new ArrayList<>();
        for(int i = 1; i < numberMonth.length; i++){
            numberMonth[i] = i + "月";
            list.add(numberMonth[i]);
        }

        //系列1
//        xAxis.setCategories(FXCollections.<String>observableArrayList(
////                Arrays.asList(firstMonth, secondMonth, thirdMonth, forthMonth, currentMonth)));

        xAxis.setCategories(FXCollections.<String>observableArrayList(list));


        XYChart.Series<String, Number> PinGlue = new XYChart.Series<>();
        PinGlue.setName("针脚黏胶");
        //系列2
        XYChart.Series<String , Number> PinAsknew = new XYChart.Series<>();
        PinAsknew.setName("针脚歪斜");

        //系列3
        XYChart.Series<String, Number> standard = new XYChart.Series<>();
        standard.setName("合格");

        //系列四
        XYChart.Series<String, Number> GlueOut = new XYChart.Series<>();
        GlueOut.setName("溢胶");


        for(int i = 1; i < numberMonth.length; i++){
            PinGlue.getData().add(new XYChart.Data<>(numberMonth[i], PinGlueNumber[i]));
            PinAsknew.getData().add(new XYChart.Data<>(numberMonth[i], PinAsknewNumber[i]));
            standard.getData().add(new XYChart.Data<>(numberMonth[i], standardNumber[i]));
            GlueOut.getData().add(new XYChart.Data<>(numberMonth[i], GlueOutNumber[i]));
        }
        //显示图表
        stackedBarChart.getData().addAll(PinGlue, PinAsknew,GlueOut,standard);
        for (final XYChart.Data<String, Number> dt : PinGlue.getData()) {
            final Node n = dt.getNode();

            n.setEffect(null);
            n.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Tooltip t = new Tooltip(dt.getYValue().toString());
                    Font value = Font.font(20);
                    t.setFont(value);
                    t.setStyle("-fx-background-color:white");
                    t.setStyle("-fx-border-color:orange");
                    t.setStyle("-fx-border-width:2");
                    t.setStyle("-fx-border-radius:3");
                    Tooltip.install(dt.getNode(), t);
                }
            });
        }

        for (final XYChart.Data<String, Number> dt : PinAsknew.getData()) {
            final Node n = dt.getNode();

            n.setEffect(null);
            n.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Tooltip t = new Tooltip(dt.getYValue().toString());
                    Font value = Font.font(20);
                    t.setFont(value);
                    t.setStyle("-fx-background-color:white");
                    t.setStyle("-fx-border-color:orange");
                    t.setStyle("-fx-border-width:2");
                    t.setStyle("-fx-border-radius:3");
                    Tooltip.install(dt.getNode(), t);
                }
            });
        }

        for (final XYChart.Data<String, Number> dt : GlueOut.getData()) {
            final Node n = dt.getNode();

            n.setEffect(null);
            n.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Tooltip t = new Tooltip(dt.getYValue().toString());
                    Font value = Font.font(20);
                    t.setFont(value);
                    t.setStyle("-fx-background-color:white");
                    t.setStyle("-fx-border-color:orange");
                    t.setStyle("-fx-border-width:2");
                    t.setStyle("-fx-border-radius:3");
                    Tooltip.install(dt.getNode(), t);
                }
            });
        }
        for (final XYChart.Data<String, Number> dt : standard.getData()) {
            final Node n = dt.getNode();

            n.setEffect(null);
            n.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    Tooltip t = new Tooltip(dt.getYValue().toString());
                    Font value = Font.font(20);
                    t.setFont(value);
                    t.setStyle("-fx-background-color:white");
                    t.setStyle("-fx-border-color:orange");
                    t.setStyle("-fx-border-width:2");
                    t.setStyle("-fx-border-radius:3");
                    Tooltip.install(dt.getNode(), t);
                }
            });
        }


    }
}

package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;

import java.net.URL;
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
        String firstMonth = month-4 + "月";
        String secondMonth = month-3 + "月";
        String thirdMonth = month-2 + "月";
        String forthMonth = month-1 + "月";
        String currentMonth = month + "月";
        //系列1
        xAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList(firstMonth, secondMonth, thirdMonth, forthMonth, currentMonth)));

        XYChart.Series<String, Number> PinGlue = new XYChart.Series<>();
        PinGlue.setName("针脚黏胶");
        PinGlue.getData().add(new XYChart.Data<>(firstMonth, 20));
        PinGlue.getData().add(new XYChart.Data<>(secondMonth, 80));
        PinGlue.getData().add(new XYChart.Data<>(thirdMonth, 40));
        PinGlue.getData().add(new XYChart.Data<>(forthMonth, 20));
        PinGlue.getData().add(new XYChart.Data<>(currentMonth, 10));
        //系列2
        XYChart.Series<String , Number> PinAsknew = new XYChart.Series<>();
        PinAsknew.setName("针脚歪斜");
        PinAsknew.getData().add(new XYChart.Data<>(firstMonth, 80));
        PinAsknew.getData().add(new XYChart.Data<>(secondMonth, 60));
        PinAsknew.getData().add(new XYChart.Data<>(thirdMonth, 65));
        PinAsknew.getData().add(new XYChart.Data<>(forthMonth, 50));
        PinAsknew.getData().add(new XYChart.Data<>(currentMonth, 10));
        //系列3
        XYChart.Series<String, Number> standard = new XYChart.Series<>();
        standard.setName("合格");
        standard.getData().add(new XYChart.Data<>(firstMonth, 10));
        standard.getData().add(new XYChart.Data<>(secondMonth, 40));
        standard.getData().add(new XYChart.Data<>(thirdMonth, 55));
        standard.getData().add(new XYChart.Data<>(forthMonth, 70));
        standard.getData().add(new XYChart.Data<>(currentMonth, 60));
        //系列四
        XYChart.Series<String, Number> GlueOut = new XYChart.Series<>();
        GlueOut.setName("溢胶");
        GlueOut.getData().add(new XYChart.Data<>(firstMonth, 20));
        GlueOut.getData().add(new XYChart.Data<>(secondMonth, 50));
        GlueOut.getData().add(new XYChart.Data<>(thirdMonth, 55));
        GlueOut.getData().add(new XYChart.Data<>(forthMonth, 70));
        GlueOut.getData().add(new XYChart.Data<>(currentMonth, 100));
        //显示图表
        stackedBarChart.getData().addAll(PinGlue, PinAsknew, standard,GlueOut);
    }
}

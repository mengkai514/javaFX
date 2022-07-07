package controllers;

import app.MyApplication;
import configs.StaticResourcesConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import service.ConveyorService;
import service.impl.ConveyorServiceImpl;
import sun.misc.BASE64Decoder;
import utils.FxmlLoader;
import utils.SocketListener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
/**
 * @description: 产品检测界面的controller
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class ProductDetectController implements Initializable {

    @FXML
    private AnchorPane anchorPanel;

    @FXML
    private TextField numberOfDefectTextField;

    @FXML
    private Button clearProductPlateBuuton1;

    @FXML
    private Button startButton;

    @FXML
    private Button clearProductPlateBuuton;

    @FXML
    private TextField cameraExposureTimeTextField;

    @FXML
    private ImageView imageView;

    @FXML
    private Button stopbutton;

    @FXML
    private TextField conveyorSpeedTextField;

    @FXML
    private TextField numberOfDetectedTextField;

    @FXML
    private TextField CameraResolutionTextField;

    @FXML
    private Circle statusCircle;

    @FXML
    private Label statusLabel;

    @FXML
    private FlowPane flowPane;

    @FXML
    private TextField numberOfGoodProductTextField;

    @FXML
    private Circle pinAskewCircle;

    @FXML
    private Circle pinGlueCircle;

    @FXML
    private TextField defectRateTextField;

    @FXML
    private Circle glueOutCircle;
    //已检测数量
    private int numberOfDetected = 0;
    //缺陷数量
    private int numberOfDefect = 0;
    //缺陷率
    private double defectRate = 0;
    //良品数量
    private int numberOfGoodProduct = 0;

    private ArrayList<ResultViewController> resultViewControllerList= new ArrayList<>();

    private ArrayList<AnchorPane> resultViewAnchorPaneList = new ArrayList<>();

    public static int currentResultViewIndex=0;
    public static int maxResultViewIndex=9;

    private MyApplication myApplication;



    public void setApp(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @FXML
    void startConveyor(ActionEvent event) {
        ConveyorService conveyorService = new ConveyorServiceImpl();
        if (conveyorService.startConveyor()) {
            //弹窗提示传送带启动成功
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("传送带启动成功");
            alert.setContentText("传送带启动成功");
            alert.showAndWait();
            //            改变状态标签的颜色为绿色
            statusCircle.setFill(javafx.scene.paint.Color.GREEN);
            statusLabel.setText("正在运行...");
        } else {
            //弹窗提示传送带启动失败
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("传送带启动失败");
            alert.setContentText("传送带启动失败");
            alert.showAndWait();
            //            改变状态标签的颜色为红色
            statusLabel.setText("未运行");
            statusCircle.setFill(javafx.scene.paint.Color.RED);
        }
    }

    @FXML
    void stopConveyor(ActionEvent event) {
        ConveyorService conveyorService = new ConveyorServiceImpl();
        if (conveyorService.stopConveyor()) {
            //弹窗提示传送带停止成功
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("传送带停止成功");
            alert.setContentText("传送带停止成功");
            alert.showAndWait();

            //            改变状态标签的颜色为红色
            statusCircle.setFill(Color.RED);
            statusLabel.setText("未运行");
        } else {
            //弹窗提示传送带启动失败
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("传送带停止失败");
            alert.setContentText("传送带停止失败");
            alert.showAndWait();
            //            改变状态标签的颜色为绿色
            statusCircle.setFill(Color.GREEN);
            statusLabel.setText("正在运行...");
        }
    }

    @FXML
    void resetPlate(ActionEvent event) {

    }

    @FXML
    void resetData(ActionEvent event) {
        numberOfDetectedTextField.setText("0");
        numberOfDefectTextField.setText("0");
        defectRateTextField.setText("0");
        numberOfGoodProductTextField.setText("0");

    }


    /**
     * 回调函数，用于设置结果在resultView上显示
     * @param imageBase64 图片的base64编码
     * @param isPinAskew 针脚是否歪斜
     * @param isPinGlue 针脚是否黏胶
     * @param isGlueOut 是否溢胶
     */
    public void setDetectResult(String imageBase64, boolean isPinAskew, boolean isPinGlue, boolean isGlueOut) {
        //更新结果
        resultViewControllerList.get(currentResultViewIndex).setDetectResult(imageBase64 ,isPinAskew, isPinGlue, isGlueOut);
        //设置当前resultView的边框样式为默认样式，并将下一个resultView的边框样式改为选中样式
        resultViewControllerList.get(currentResultViewIndex).setDefaultBorderStyle();
        resultViewControllerList.get((currentResultViewIndex+1)%(maxResultViewIndex+1)).setSelectedBorderStyle();
        //更新currentResultViewIndex
        if(currentResultViewIndex<maxResultViewIndex){
            currentResultViewIndex++;
        }else{
            currentResultViewIndex=0;
        }



//        更新批次统计信息
        numberOfDetectedTextField.setText(String.valueOf(++numberOfDetected));
        if (isPinAskew == false || isPinGlue == false || isGlueOut == false) {
            numberOfDefectTextField.setText(String.valueOf(++numberOfDefect));
        }
        defectRate = ((double) numberOfDefect) / numberOfDetected;

        defectRateTextField.setText(String.valueOf(defectRate * 100).substring(0, 4) + "%");

        numberOfGoodProduct = numberOfDetected - numberOfDefect;
        numberOfGoodProductTextField.setText(String.valueOf(numberOfGoodProduct));


    }

    /**
     * 重置标记缺陷类型的圆圈的颜色
     */
    public void resetDefectCircleColor() {
        pinAskewCircle.setFill(Color.GREEN);
        pinGlueCircle.setFill(Color.GREEN);
        glueOutCircle.setFill(Color.GREEN);
    }
    /**
     * 在加载本页面时，并获取本Controller的示例时，会调用此方法进行初始化
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //创建socket监听线程实例
        SocketListener socketListener = new SocketListener();

        //传输本controller1的实例，以便在监听线程中调用回调函数更新界面
        socketListener.setProductDetectController(this);

        //设置flowPane中内容的间距
        flowPane.setHgap(10);
        flowPane.setVgap(10);

        //在flowPane中添加组件
        ArrayList<Object> reList = new ArrayList<>();
        ResultViewController resultViewController=null;
        AnchorPane resultViewAnchorPane=null;
//        在界面中的flowPane中添加多个resultView（检测结果的View）
        int i=0;
        while(i<=ProductDetectController.maxResultViewIndex){
            //加载resultView的Controller 和 AnchorPane对象
            reList = FxmlLoader.addFxml(StaticResourcesConfig.RESULTVIEW_VIEW_PATH);
            resultViewController = (ResultViewController) reList.get(0);
            resultViewAnchorPane = (AnchorPane) reList.get(1);
            //设置每个resultView左上角的label显示的字样
            resultViewController.setIndexLabel("数码管"+String.valueOf(i+1));
            //将resultView载入到flowPane中
            flowPane.getChildren().add(resultViewAnchorPane);
//            将resultViewController保存在一个List方便后面调用
            resultViewControllerList.add(resultViewController);
            i++;
        }
        //设置第一个resultView的边框样式为选中样式(不知道为什么没有效果)
//        resultViewControllerList.get(0).setSelectedBorderStyle();
        //启动socket监听线程
        new Thread(socketListener).start();
    }
}

package controllers;

import app.MyApplication;
import configs.StaticResourcesConfig;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import utils.FxmlLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @description: 技术管理员登录后显示的界面框架。
 * 包括“账号管理界面”、“产品检测界面”等界面都加载到此界面中controller
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class TechFrameController implements Initializable {
    private MyApplication myApplication;

    private AccountManageController accountManageController;
    private EquipmentSettingController equipmentSettingController;
    private ProductDetectController productDetectController;

    private AnchorPane accountManageAnchorPane;
    private AnchorPane equipmentSettingAnchorPane;
    private AnchorPane productDetectAnchorPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    @FXML
    private Button accountManageButton;

    @FXML
    private Button equipmentSettingButton;

    @FXML
    private Button productDetectButton;

    @FXML
    void onAccountManageButtonClick(ActionEvent event) {
        accountManageAnchorPane.toFront();
    }

    @FXML
    void onEquipmentSettingButtonClick(ActionEvent event) {
//        equipmentSetting.toFront();
        equipmentSettingAnchorPane.toFront();
    }

    @FXML
    void onProductDetectButtonClick(ActionEvent event) {
//        productDetect.toFront();
        productDetectAnchorPane.toFront();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<Object> ReList = null;

        //加载账户管理界面
        ReList = FxmlLoader.addFxml(StaticResourcesConfig.ACCOUNTMANAGE_VIEW_PATH);
        accountManageController = (AccountManageController) ReList.get(0);
        accountManageAnchorPane = (AnchorPane) ReList.get(1);
        //绑定长宽到父元素stackPane的长宽
        accountManageAnchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        accountManageAnchorPane.prefWidthProperty().bind(stackPane.widthProperty());


        //加载产品检测界面
        ReList = FxmlLoader.addFxml(StaticResourcesConfig.PRODUCTDETECT_VIEW_PATH);
        productDetectController = (ProductDetectController) ReList.get(0);
        productDetectAnchorPane = (AnchorPane) ReList.get(1);
        //绑定长宽到父元素stackPane的长宽
        productDetectAnchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        productDetectAnchorPane.prefWidthProperty().bind(stackPane.widthProperty());


        //加载设备维护界面
        ReList = FxmlLoader.addFxml(StaticResourcesConfig.EQUIPMENTSETTING_VIEW_PATH);
        equipmentSettingController = (EquipmentSettingController) ReList.get(0);
        equipmentSettingAnchorPane = (AnchorPane) ReList.get(1);
        //绑定长宽到父元素stackPane的长宽
        equipmentSettingAnchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        equipmentSettingAnchorPane.prefWidthProperty().bind(stackPane.widthProperty());

        //以上三个放入stackPane显示
        stackPane.getChildren().addAll(equipmentSettingAnchorPane, productDetectAnchorPane, accountManageAnchorPane);

    }


    public void setApp(MyApplication myApplication) {
        this.myApplication = myApplication;
    }
}


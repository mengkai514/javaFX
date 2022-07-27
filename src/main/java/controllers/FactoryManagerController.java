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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
public class FactoryManagerController implements Initializable {
    private MyApplication myApplication;

    private AccountManageController accountManageController;
    private EquipmentSettingController equipmentSettingController;
    private ProductDetectController productDetectController;
    private DataAnalyzeController dataAnalyzeController;

    private AnchorPane accountManageAnchorPane;
    private AnchorPane equipmentSettingAnchorPane;
    private AnchorPane productDetectAnchorPane;
    private AnchorPane dataAnalyzeAnchorPane;

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
    private Button exitSystemButton;

    @FXML
    void onAccountManageButtonClick(ActionEvent event) {
        equipmentSettingAnchorPane.setVisible(false);
        productDetectAnchorPane.setVisible(false);
        dataAnalyzeAnchorPane.setVisible(false);
        accountManageAnchorPane.toFront();
        accountManageAnchorPane.setVisible(true);

    }

    @FXML
    void onEquipmentSettingButtonClick(ActionEvent event) {
//        equipmentSetting.toFront();
        productDetectAnchorPane.setVisible(false);
        dataAnalyzeAnchorPane.setVisible(false);
        accountManageAnchorPane.setVisible(false);
        equipmentSettingAnchorPane.toFront();
        equipmentSettingAnchorPane.setVisible(true);

    }

    @FXML
    void onProductDetectButtonClick(ActionEvent event) {
//        productDetect.toFront();
        dataAnalyzeAnchorPane.setVisible(false);
        accountManageAnchorPane.setVisible(false);
        equipmentSettingAnchorPane.setVisible(false);
        productDetectAnchorPane.toFront();
        productDetectAnchorPane.setVisible(true);

    }

    @FXML
    public void onDataAnalyzeButtonClick(ActionEvent event) {
        productDetectAnchorPane.setVisible(false);
        accountManageAnchorPane.setVisible(false);
        equipmentSettingAnchorPane.setVisible(false);
        dataAnalyzeAnchorPane.toFront();
        dataAnalyzeAnchorPane.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ArrayList<Object> ReList = null;

        // 设置退出系统图标
        Image image = new Image("file:src/main/resources/image/退出4.png");
        ImageView imageView = new ImageView(image);
        exitSystemButton.setGraphic(imageView);

        //加载账户管理界面
        ReList = FxmlLoader.addFxml(StaticResourcesConfig.ACCOUNTMANAGE_VIEW_PATH);
        accountManageController = (AccountManageController) ReList.get(0);
        accountManageAnchorPane = (AnchorPane) ReList.get(1);
        accountManageAnchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        accountManageAnchorPane.prefWidthProperty().bind(stackPane.widthProperty());


        //加载产品检测界面
        ReList = FxmlLoader.addFxml(StaticResourcesConfig.PRODUCTDETECT_VIEW_PATH);
        productDetectController = (ProductDetectController) ReList.get(0);
        productDetectAnchorPane = (AnchorPane) ReList.get(1);
        productDetectAnchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        productDetectAnchorPane.prefWidthProperty().bind(stackPane.widthProperty());
//        productDetectAnchorPane.setVisible(false);


        //加载设备维护界面
        ReList = FxmlLoader.addFxml(StaticResourcesConfig.EQUIPMENTSETTING_VIEW_PATH);
        equipmentSettingController = (EquipmentSettingController) ReList.get(0);
        equipmentSettingAnchorPane = (AnchorPane) ReList.get(1);
        equipmentSettingAnchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        equipmentSettingAnchorPane.prefWidthProperty().bind(stackPane.widthProperty());
//        equipmentSettingAnchorPane.setVisible(false);

        //加载数据分析界面
        ReList = FxmlLoader.addFxml(StaticResourcesConfig.DATA_ANALYZE_VIEW_PATH);
        dataAnalyzeController = (DataAnalyzeController) ReList.get(0);
        dataAnalyzeAnchorPane = (AnchorPane) ReList.get(1);
        dataAnalyzeAnchorPane.prefHeightProperty().bind(stackPane.heightProperty());
        dataAnalyzeAnchorPane.prefWidthProperty().bind(stackPane.widthProperty());
//        dataAnalyzeAnchorPane.setVisible(false);

        //以上四个放入stackPane显示
        stackPane.getChildren().addAll(dataAnalyzeAnchorPane, equipmentSettingAnchorPane, productDetectAnchorPane, accountManageAnchorPane);

    }

    @FXML
    void onExitSystemButtonClick(ActionEvent event){
        System.exit(0);
    }


    public void setApp(MyApplication myApplication) {
        this.myApplication = myApplication;
    }


}


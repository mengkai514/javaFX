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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @description: ��������Ա��¼����ʾ�Ľ����ܡ�
 * �������˺Ź������桱������Ʒ�����桱�Ƚ��涼���ص��˽�����controller
 * @author: ����
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

        SimpleDoubleProperty heightProperty = new SimpleDoubleProperty(stackPane.prefHeightProperty().doubleValue());
        SimpleDoubleProperty widthProperty = new SimpleDoubleProperty(stackPane.prefWidthProperty().doubleValue());
        ArrayList<Object> ReList = null;

        //�����˻���������
        ReList = addFxml(StaticResourcesConfig.ACCOUNTMANAGE_VIEW_PATH, heightProperty, widthProperty);
        accountManageController = (AccountManageController) ReList.get(0);
        accountManageAnchorPane = (AnchorPane) ReList.get(1);
        accountManageAnchorPane.prefHeightProperty().bind(heightProperty);
        accountManageAnchorPane.prefWidthProperty().bind(widthProperty);


        //���ز�Ʒ������
        ReList = addFxml(StaticResourcesConfig.PRODUCTDETECT_VIEW_PATH, heightProperty, widthProperty);
        productDetectController = (ProductDetectController) ReList.get(0);
        productDetectAnchorPane = (AnchorPane) ReList.get(1);
        productDetectAnchorPane.prefHeightProperty().bind(heightProperty);
        productDetectAnchorPane.prefWidthProperty().bind(widthProperty);


        //�����豸ά������
        ReList = addFxml(StaticResourcesConfig.EQUIPMENTSETTING_VIEW_PATH, heightProperty, widthProperty);
        equipmentSettingController = (EquipmentSettingController) ReList.get(0);
        equipmentSettingAnchorPane = (AnchorPane) ReList.get(1);
        equipmentSettingAnchorPane.prefHeightProperty().bind(heightProperty);
        equipmentSettingAnchorPane.prefWidthProperty().bind(widthProperty);

        //������������stackPane��ʾ
        stackPane.getChildren().addAll(equipmentSettingAnchorPane, productDetectAnchorPane, accountManageAnchorPane);

    }

    private ArrayList<Object> addFxml(String fxmlPath, SimpleDoubleProperty stackPaneHeightProperty, SimpleDoubleProperty stackPaneWidthProperty) {
        ArrayList<Object> objectsToReturn = new ArrayList<>();
        FXMLLoader loader = new FXMLLoader();

        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(MyApplication.class.getResource(fxmlPath));


        InputStream in = MyApplication.class.getResourceAsStream(fxmlPath);

        try {
            AnchorPane page = (AnchorPane) loader.load(in);
            objectsToReturn.add((Initializable) loader.getController());
            objectsToReturn.add(page);
//            page.widthProperty().bind(stackPaneWidthProperty);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            return objectsToReturn;
        }

    }

    public void setApp(MyApplication myApplication) {
        this.myApplication = myApplication;
    }
}

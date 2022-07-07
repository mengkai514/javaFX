package controllers;

import com.alibaba.fastjson.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Camera;
import service.GetParamsService;
import service.SetParamsService;
import service.impl.GetParamsServiceImpl;
import service.impl.SetParamsServiceImpl;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @description: 设备维护界面的controller
 * @author: 黄涛
 * @date: 2022-7-4
 */

public class EquipmentSettingController implements Initializable {
    @FXML
    private TextField cameraHeight;
    @FXML
    private TextField acquisitionFrameRate;
    @FXML
    private TextField exposureTime;
    @FXML
    private TextField cameraWidth;
    @FXML
    private TextField conveyorSpeed;
    @FXML
    private Button toProductDetectView;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GetParamsService getParams = new GetParamsServiceImpl();
        String params = getParams.getParams();

        if(params != null){
            Map<String, Object> paramsMap = JSONObject.parseObject(params,Map.class);
            if(paramsMap.get("type").equals("getDeviceParamsReply")){
                Map resultMap = (Map)paramsMap.get("content");
                int height = (int) resultMap.get("Height");
                int width = (int) resultMap.get("Width");
                Object acquisitionF = resultMap.get("AcquisitionFrameRate");
                Object exposuretime = resultMap.get("ExposureTime");
                Object conveyorspeed = resultMap.get("conveyorSpeed");

                cameraHeight.setText(Integer.toString(height));
                cameraWidth.setText(Integer.toString(width));
                acquisitionFrameRate.setText(acquisitionF.toString());
                exposureTime.setText(exposuretime.toString());
                conveyorSpeed.setText(conveyorspeed.toString());
            }
        }

    }

    @FXML
    public void alterParams(ActionEvent actionEvent) {
        //获取界面上的参数值
        String height = cameraHeight.getText();
        String width = cameraWidth.getText();
        String acquisitionRate = acquisitionFrameRate.getText();
        String exTime = exposureTime.getText();
        String conveyorspeed = conveyorSpeed.getText();
        Camera camera = new Camera(height,width,acquisitionRate,exTime,conveyorspeed);
        SetParamsService setParamsService = new SetParamsServiceImpl();
        boolean result = setParamsService.setParams(camera);
        if (result) {
            //弹窗提示传送带停止成功
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("修改成功");
            alert.setContentText("修改成功");
            alert.showAndWait();
        } else {
            //弹窗提示传送带启动失败
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("修改失败");
            alert.setContentText("修改失败");
            alert.showAndWait();
        }

    }
}

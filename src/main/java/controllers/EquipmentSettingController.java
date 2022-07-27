package controllers;

import com.alibaba.fastjson.JSONObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import model.Camera;
import service.GetParamsService;
import service.SetParamsService;
import service.impl.GetParamsServiceImpl;
import service.impl.SetParamsServiceImpl;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    @FXML
    private TextField cameraLeftConnect;
    @FXML
    private TextField PLCConnect;
    @FXML
    private Button cameraTips;
    @FXML
    private Button IPTips;
    @FXML
    private ImageView cameraShow;
    @FXML
    private ComboBox selectCameraOrientation;
    @FXML
    private ComboBox selectCamera;
    @FXML
    private Label tipsLabel;
    @FXML
    private Button checkCamera;
    @FXML
    private Button defaultSetting;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        GetParamsService getParams = new GetParamsServiceImpl();
        String params = getParams.getParams();

        //设置提示图标
        Image image = new Image("file:src/main/resources/image/tips.png");
        ImageView imageView = new ImageView(image);
        ImageView imageViewIPIcon = new ImageView(image);
        cameraTips.setGraphic(imageView);
        IPTips.setGraphic(imageViewIPIcon);

        if(params != null){
            Map<String, Object> paramsMap = JSONObject.parseObject(params,Map.class);
            if(paramsMap.get("type").equals("getDeviceParamsReply")){
                Map resultMap = (Map)paramsMap.get("content");
                int height = (int) resultMap.get("Height");
                int width = (int) resultMap.get("Width");
                Object acquisitionF = resultMap.get("AcquisitionFrameRate");
                Object exposuretime = resultMap.get("ExposureTime");
                Object conveyorspeed = resultMap.get("conveyorSpeed");
                String cameraLeftIsConnect = (String) resultMap.get("cameraLeftIsConnect");
                String PLCIsConnect = (String) resultMap.get("PLCIsConnect");

                cameraHeight.setText(Integer.toString(height));
                cameraWidth.setText(Integer.toString(width));
                acquisitionFrameRate.setText(acquisitionF.toString());
                exposureTime.setText(exposuretime.toString());
                conveyorSpeed.setText(conveyorspeed.toString());
                if(cameraLeftIsConnect.equals("True")){
                    cameraLeftConnect.setText("已连接");
                }
                if(PLCIsConnect.equals("True")){
                    PLCConnect.setText("已连接");
                }
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
        // 获取下拉框选中的相机
        String cameraOption = (String) selectCamera.getValue();
        // 获取下拉框选中的相机方位
        String cameraOrientation = (String) selectCameraOrientation.getValue();

        Camera camera = new Camera(height,width,acquisitionRate,exTime,conveyorspeed,cameraOption,cameraOrientation);
        SetParamsService setParamsService = new SetParamsServiceImpl(this);
        boolean result = setParamsService.setParams(camera);
        if (result) {
            //弹窗提示修改成功
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("修改成功");
            alert.setContentText("修改成功");
            alert.showAndWait();
        } else {
            //弹窗提示修改失败
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("修改失败");
            alert.setContentText("修改失败");
            alert.showAndWait();
        }
    }

    public void setParamsWithPic(int height, int width, Object acquisitionF,
                          Object exposuretime,Object conveyorspeed){
        cameraHeight.setText(Integer.toString(height));
        cameraWidth.setText(Integer.toString(width));
        acquisitionFrameRate.setText(acquisitionF.toString());
        exposureTime.setText(exposuretime.toString());
        conveyorSpeed.setText(conveyorspeed.toString());
    }

    public void setCheckCameraPic(String base64) {
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(base64);
            for (int i = 0; i < b.length ; i++) {
                if (b[i] < 0){
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream("src\\main\\resources\\image\\alterResult.jpg");
            out.write(b);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image image = new Image("file:src\\main\\resources\\image\\alterResult.jpg");
        cameraShow.setImage(image);
    }

    public void getTips(MouseEvent mouseEvent) {
//        Tooltip tooltip = new Tooltip();
//        tooltip.setText("相机高度为2的倍数，相机宽度为4的倍数\n"+"曝光率为0.400000~500.000000\n" +
//                "曝光时间为27~2500000");
//        cameraTips.setTooltip(tooltip);
        tipsLabel.setVisible(true);
        tipsLabel.setText("温馨提示：相机高度为2的倍数，相机宽度为4的倍数。曝光率为0.400000~500.000000。曝光时间为27~2500000");
        tipsLabel.setWrapText(true);
    }


    public void getIPTips(MouseEvent mouseEvent) {
//        Tooltip tooltip = new Tooltip();
//        tooltip.setText("电脑的IP地址，PIC的IP地址");
//        IPTips.setTooltip(tooltip);
        tipsLabel.setVisible(true);
        tipsLabel.setText("温馨提示：电脑的IP地址，PIC的IP地址");
    }

    public void getCheckPic(ActionEvent actionEvent) {
        //获取界面上的参数值
        String height = cameraHeight.getText();
        String width = cameraWidth.getText();
        String acquisitionRate = acquisitionFrameRate.getText();
        String exTime = exposureTime.getText();
        String conveyorspeed = conveyorSpeed.getText();
        // 获取下拉框选中的相机
        String cameraOption = (String) selectCamera.getValue();
        // 获取下拉框选中的相机方位
        String cameraOrientation = (String) selectCameraOrientation.getValue();

        Camera camera = new Camera(height,width,acquisitionRate,exTime,conveyorspeed,cameraOption,cameraOrientation);
        SetParamsService setParamsService = new SetParamsServiceImpl(this);
        setParamsService.getCheckCameraPic(camera);
    }


    public void setDefaultParams(ActionEvent actionEvent) {
        //获取界面上的参数值
        String height = "2048";
        String width = "3072";
        String acquisitionRate = "13.6000";
        String exTime = "58806.0";
        String conveyorspeed = "80.0";
        // 获取下拉框选中的相机
        String cameraOption = "相机1";
        // 获取下拉框选中的相机方位
        String cameraOrientation = "左";

        Camera camera = new Camera(height,width,acquisitionRate,exTime,conveyorspeed,cameraOption,cameraOrientation);
        SetParamsService setParamsService = new SetParamsServiceImpl(this);
        boolean result = setParamsService.setParams(camera);
        if (result) {
            //弹窗提示修改成功
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("保存成功");
            alert.setContentText("保存成功");
            alert.showAndWait();
        } else {
            //弹窗提示修改失败
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提示");
            alert.setHeaderText("保存成功");
            alert.setContentText("保存成功");
            alert.showAndWait();
        }
    }
}

package sample;

import com.alibaba.fastjson.JSONObject;
import model.Camera;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Map;


public class Controller {
    @FXML
    private Button deviceParams;
    @FXML
    private TextField cameraHeight;
    @FXML
    private TextField cameraWidth;
    @FXML
    private TextField acquisitionFrameRate;
    @FXML
    private TextField exposureTime;
    @FXML
    private TextField conveyorSpeed;
    @FXML
    private Button toProductDetectView;


    static ArrayList<String> paramsList = new ArrayList<String>();
    // 开启传送带
    public void runDevice(MouseEvent mouseEvent) {
        SocketCommunication startConveyor = new SocketCommunication();
        startConveyor.send("startConveyor");
    }

    public void runCamera(MouseEvent mouseEvent) {
        SocketCommunication cameraStart = new SocketCommunication();
        cameraStart.send("cameraStart");
    }

    // 关闭传送带
    public void stopDevice(MouseEvent mouseEvent) {
        SocketCommunication stopConveyor = new SocketCommunication();
        stopConveyor.send("stopConveyor");
    }

    public void toOpenDeviceParamsView(MouseEvent mouseEvent) {
        Stage deviceParamsStage = (Stage) deviceParams.getScene().getWindow();
        deviceParamsStage.close();
        try {
            long startTime = System.currentTimeMillis();
            SocketCommunication getDeviceParams = new SocketCommunication();
            String deviceParams = getDeviceParams.send("getDeviceParams");
            System.out.println(deviceParams);
            JSONObject params = JSONObject.parseObject(deviceParams);

            long endTime = System.currentTimeMillis();
            System.out.println("获取设备参数时间:"+(endTime-startTime));

            String paramsData = params.getString("content");
            Map<String,String> paramsMap = (Map) JSONObject.parse(paramsData);
            for (Object map : paramsMap.entrySet()) {
                paramsList.add(String.valueOf(((Map.Entry)map).getValue()));
            }

            System.out.println(paramsList);

            new DeviceParamsView().start(deviceParamsStage);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void alterParams(MouseEvent mouseEvent) {

        //获取界面上的参数值
        String height = cameraHeight.getText();
        String width = cameraWidth.getText();
        String acquisitionRate = acquisitionFrameRate.getText();
        String exTime = exposureTime.getText();
        String conveyorspeed = conveyorSpeed.getText();

//        Camera camera = new Camera(height,width,acquisitionRate,exTime,conveyorspeed);
//        System.out.println(camera);
//        JSONObject jsonObject = new JSONObject();
//        JSONObject json = (JSONObject) jsonObject.toJSON(camera);
//        System.out.println(json);
//        JSONObject cameraJson= (JSONObject) JSONObject.toJSON(camera);
        JSONObject cameraJson = new JSONObject();
        cameraJson.put("type","alterParams");
//        cameraJson.put("content",camera);
        System.out.println("camera对象的json:" + cameraJson.toString());
//        SocketCommunication socketCommunication = new SocketCommunication(cameraJson.toString());


        // 跳转技术员产品检测页面
        Stage productDetectView = (Stage) toProductDetectView.getScene().getWindow();
        productDetectView.close();

        try {
            new Check().start(productDetectView);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}

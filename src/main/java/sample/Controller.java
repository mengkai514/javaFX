package sample;

import com.alibaba.fastjson.JSONObject;
import entity.Camera;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

    public void runDevice(MouseEvent mouseEvent) {
        SocketCommunication socketCommunication = new SocketCommunication("deviceStart");
//        socketCommunication.communite("deviceStart");
    }

    public void runCamera(MouseEvent mouseEvent) {
        SocketCommunication socketCommunication = new SocketCommunication("cameraStart");
//        socketCommunication.communite("cameraStart");
    }

    public void stopDevice(MouseEvent mouseEvent) {
        SocketCommunication socketCommunication = new SocketCommunication("deviceStop");
//        socketCommunication.communite("deviceStop");
    }

    public void toOpenDeviceParamsView(MouseEvent mouseEvent) {
        Stage deviceParamsStage = (Stage) deviceParams.getScene().getWindow();
        deviceParamsStage.close();
        try {

            SocketCommunication socketCommunication = new SocketCommunication("getDeviceParams");
//            JSONObject params = socketCommunication.communite("getDeviceParams");
            JSONObject params = socketCommunication.res;
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
//        String height = cameraHeight.getText();
//        String width = cameraWidth.getText();
//        String acquisitionRate = acquisitionFrameRate.getText();
//        String exTime = exposureTime.getText();
//        String conveyorspeed = conveyorSpeed.getText();
//
//        Camera camera = new Camera(height,width,acquisitionRate,exTime,conveyorspeed);
//        System.out.println(camera);
//        JSONObject jsonObject = new JSONObject();
//        JSONObject json = (JSONObject) jsonObject.toJSON(camera);
//        System.out.println(json);
//
//        SocketCommunication socketCommunication = new SocketCommunication(json.toString());


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

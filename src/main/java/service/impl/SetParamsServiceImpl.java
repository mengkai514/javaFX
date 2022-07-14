package service.impl;

import com.alibaba.fastjson.JSONObject;
import controllers.EquipmentSettingController;
import javafx.application.Platform;
import model.Camera;
import sample.SocketCommunication;
import service.SetParamsService;
import java.util.Map;


public class SetParamsServiceImpl implements SetParamsService {
    EquipmentSettingController equipmentSettingController;
    int height;
    int width;
    Object acquisitionF;
    Object exposuretime;
    Object conveyorspeed;
    String base64;
    public SetParamsServiceImpl(EquipmentSettingController equipmentSettingController){
        this.equipmentSettingController = equipmentSettingController;
    }
    @Override
    public boolean setParams(Camera camera) {
        JSONObject cameraJson = new JSONObject();
        cameraJson.put("type","alterParams");
        cameraJson.put("content",camera);
        System.out.println("camera对象的json:" + cameraJson.toString());
        String retsString = new SocketCommunication().send(cameraJson.toString());
        Map<String, Object> retMap = JSONObject.parseObject(retsString, Map.class);
        if (retMap.get("type").equals("setDeviceParamsReply")) {
            Map resultMap = (Map) retMap.get("content");
            height = (int) resultMap.get("Height");
            width = (int) resultMap.get("Width");
            acquisitionF = resultMap.get("AcquisitionFrameRate");
            exposuretime = resultMap.get("ExposureTime");
            conveyorspeed = resultMap.get("conveyorSpeed");
            base64 = (String) resultMap.get("ImageResult");
        }
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                equipmentSettingController.setParamsWithPic(height,width,acquisitionF,exposuretime,conveyorspeed,base64);
            }
        });
        if (retMap.get("statusCode").equals("200") || (Integer) retMap.get("statusCode") == 200){
            return true;
        } else {
            return false;
        }

    }
}

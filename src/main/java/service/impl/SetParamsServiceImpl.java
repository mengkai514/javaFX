package service.impl;

import com.alibaba.fastjson.JSONObject;
import model.Camera;
import sample.SocketCommunication;
import service.SetParamsService;

import java.util.Map;

public class SetParamsServiceImpl implements SetParamsService {
    @Override
    public boolean setParams(Camera camera) {
        JSONObject cameraJson = new JSONObject();
        cameraJson.put("type","alterParams");
        cameraJson.put("content",camera);
        System.out.println("camera对象的json:" + cameraJson.toString());
        String retsString = new SocketCommunication().send(cameraJson.toString());
        Map retMap = JSONObject.parseObject(retsString, Map.class);
        if(retMap==null){
            return false;
        }
        if (retMap.get("statusCode").equals("200") || (Integer) retMap.get("statusCode") == 200) {
            return true;
        } else {
            return false;
        }
    }
}

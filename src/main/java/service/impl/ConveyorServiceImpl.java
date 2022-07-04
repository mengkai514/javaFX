package service.impl;

import DAO.SocketSender;
import com.alibaba.fastjson.JSONObject;
import service.ConveyorService;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ConveyorServiceImpl implements ConveyorService {

    @Override
    public boolean startConveyor() {
        //            ����Э��map����ת��δjson��ʽ��Э��
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", "startConveyor");
        //            ��mapת��Ϊjson��ʽ���ַ���
        JSONObject jsonObject = new JSONObject(dataMap);
        String sendString = jsonObject.toJSONString();
        String retsString = new SocketSender().send(sendString, 9999);
        Map retMap = JSONObject.parseObject(retsString, Map.class);
        if (retMap.get("statusCode").equals("200") || (Integer) retMap.get("statusCode") == 200) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean stopConveyor() {
        //            ����Э��map����ת��δjson��ʽ��Э��
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("type", "stopConveyor");
        //            ��mapת��Ϊjson��ʽ���ַ���
        JSONObject jsonObject = new JSONObject(dataMap);
        String sendString = jsonObject.toJSONString();
        String retsString = new SocketSender().send(sendString, 9999);
        Map retMap = JSONObject.parseObject(retsString, Map.class);
        if (retMap.get("statusCode").equals("200") || (Integer) retMap.get("statusCode") == 200) {
            return true;
        } else {
            return false;
        }
    }
}

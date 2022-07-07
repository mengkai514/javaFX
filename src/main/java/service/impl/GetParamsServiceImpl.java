package service.impl;

import com.alibaba.fastjson.JSONObject;
import sample.DeviceParamsView;
import sample.SocketCommunication;
import service.GetParamsService;

public class GetParamsServiceImpl implements GetParamsService {

    @Override
    public String getParams() {
        String sendString = "getDeviceParams";
        String retsString = new SocketCommunication().send(sendString);

        System.out.println(retsString);
        return retsString;


    }
}

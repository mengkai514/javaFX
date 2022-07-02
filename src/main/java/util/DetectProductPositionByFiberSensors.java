package util;

import com.alibaba.fastjson.JSONObject;
import sample.SocketCommunication;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DetectProductPositionByFiberSensors {
    public boolean detectProductPosition(){
        SocketCommunication listenProductPosition = new SocketCommunication("listenProductPosition");
//        JSONObject productPosition = listenProductPosition.communite("listenProductPosition");
        JSONObject productPosition = listenProductPosition.res;
        System.out.println(productPosition);
        String isArrive = productPosition.getString("content");
        if(isArrive.equals("True")) {
            return true;
        }else{
            return false;
        }

    }
}

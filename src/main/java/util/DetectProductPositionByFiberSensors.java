package util;

import com.alibaba.fastjson.JSONObject;
import sample.SocketCommunication;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class DetectProductPositionByFiberSensors {
    public boolean detectProductPosition(){
        long startTime = System.currentTimeMillis();
//        SocketCommunication listenProductPosition = new SocketCommunication("listenProductPosition");
////        JSONObject productPosition = listenProductPosition.communite("listenProductPosition");
//        JSONObject productPosition = listenProductPosition.res;

        long endTime = System.currentTimeMillis();
        System.out.println("获取传感器时间:"+(endTime-startTime));
//        System.out.println(productPosition);
//        String isArrive = productPosition.getString("content");
//        if(isArrive.equals("True")) {
//            return true;
//        }else{
//            return false;
//        }
        return false;

    }
}

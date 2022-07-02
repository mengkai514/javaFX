package util;

import com.alibaba.fastjson.JSONObject;
import sample.Main;
import sample.SocketCommunication;
import sun.misc.BASE64Decoder;

import java.io.*;

public class ImageCatch implements Runnable {

    private Main mainApp;

    public ImageCatch(Main mainApp){
        this.mainApp = mainApp;
    }

    public void run() {
        SocketCommunication catchImage = new SocketCommunication("catchImage");
//        JSONObject productPosition = catchImage.communite("catchImage");
        JSONObject productPosition = catchImage.res;

//        // 创建一个打印输出流，输出的目标是D盘下的1.txt文件
//        PrintStream ps = null;
//        try {
//            ps = new PrintStream("D:\\1.txt");
//            //可能会出现异常，直接throws就行了
//            System.setOut(ps);
//            System.out.println("productPosition:::" + productPosition);
//            ps.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        String catchImageResult = productPosition.getString("content");

        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(catchImageResult);
            for (int i = 0; i < b.length ; i++) {
                if (b[i] < 0){
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream("src\\img\\1.jpg");
            out.write(b);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(catchImageResult);
    }
}

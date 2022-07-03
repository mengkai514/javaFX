package util;

import com.alibaba.fastjson.JSONObject;
import sample.Main;
import sample.PythonImageConnect;
import sample.SocketCommunication;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageCatch implements Runnable {

//    public static InputStream bytesImage;

    public static Main mainApp;

//    public ImageCatch(){}

    public ImageCatch(Main mainApp){
        this.mainApp = mainApp;
    }

    public void run() {
//        SocketCommunication catchImage = new SocketCommunication("catchImage");

        long startTime = System.currentTimeMillis();
        PythonImageConnect catchImage = new PythonImageConnect();
        long endTime = System.currentTimeMillis();
        System.out.println("得到图片时间："+(endTime-startTime));
//        JSONObject productPosition = catchImage.communite("catchImage");

        long dealStartTime = System.currentTimeMillis();
        JSONObject imageResult = catchImage.image;

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

        String catchImageResult = imageResult.getString("imageReturn");
//        byte[] imageBytes = catchImageResult.getBytes();
        System.out.println("图片到达");
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
//        bytesImage = new ByteArrayInputStream(imageBytes);
//        try {
//            bytesImage = ImageIO.read(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(bytesImage);
        mainApp.flag = true;
        long dealEndTime = System.currentTimeMillis();
        System.out.println("处理图片时间："+(dealEndTime-dealStartTime));
    }
}

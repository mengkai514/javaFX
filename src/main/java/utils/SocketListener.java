package utils;

import com.alibaba.fastjson.JSONObject;
import controllers.ProductDetectController;
import javafx.application.Platform;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * @description: socket监听线程，监听来自python的socket连接
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class SocketListener implements Runnable {

    private static ProductDetectController productDetectController;

    @Override
    public void run() {

        //建立服务器端Socket对象，指定绑定的端口号
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(2580);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {//监听socket连接
            try {
                //调用accept()方法开始监听，等待客户端的连接
                Socket socket = serverSocket.accept();
                //获取输入流，读取客户端发送过来的信息
                InputStream inputStream = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                //获取输出流，向客户端发送信息
                OutputStream outputStream = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "utf-8"));

                //读取客户端发送过来的信息
                String recvStr = br.readLine();
                System.out.println("recvStr"+recvStr);
                if (recvStr != null) {
//                    //将recvStr这个json转换Map对象
                    Map<String, Object> recvMap = JSONObject.parseObject(recvStr, Map.class);
//                    //如果是告知检测到了数码管
//                    if (recvMap.get("type").equals("productDetected")) {
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                //显示对于图片给用户
//                                productDetectController.setImageByPath("file:src/main/resources/image/positionDetected.jpg");
//                                //重置检测结果图片下方的三个圆点的颜色为绿色
//                                productDetectController.resetDefectCircleColor();
//                            }
//                        });
//                    }
//                    //如果是告知抓取到了图片
//                    else if (recvMap.get("type").equals("ImageCaught")) {
//                        Platform.runLater(new Runnable() {
//                            @Override
//                            public void run() {
//                                productDetectController.setImageByPath("file:src/main/resources/image/computing.jpg");
//                            }
//                        });
//                    }
                    //如果是设置结果集
                    if (recvMap.get("type").equals("setDetectResult")) {
                        Map resultMap = (Map) recvMap.get("content");
                        Boolean isPinAsknew = (Boolean) resultMap.get("isPinAsknew");
                        Boolean isPinGlue = (Boolean) resultMap.get("isPinGlue");
                        Boolean isGlueOut = (Boolean) resultMap.get("isGlueOut");
                        String Base64Data = (String) resultMap.get("ImageResult");
                        //调用回调函数更新界面
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                productDetectController.setDetectResult(Base64Data, isPinAsknew, isPinGlue, isGlueOut);
                            }
                        });
                    } else {
                        System.out.println("命令未定义");
                    }
                }
                //关闭输出流、输入流、Socket
                outputStream.close();
                inputStream.close();
                br.close();
                bw.close();
                socket.close();
                //severSocket不能在此关闭，否则serverSocket.accept()会报错closed
                //serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //线程睡眠5秒
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setProductDetectController(ProductDetectController productDetectController) {
        this.productDetectController = productDetectController;
    }
}
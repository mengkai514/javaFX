package utils;

import com.alibaba.fastjson.JSONObject;
import controllers.ProductDetectController;
import javafx.application.Platform;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * @description: socket�����̣߳���������python��socket����
 * @author: ����
 * @date: 2022-7-4
 */
public class SocketListener implements Runnable {

    private ProductDetectController productDetectController;

    @Override
    public void run() {

        //������������Socket����ָ���󶨵Ķ˿ں�
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {//����socket����
            try {
                //����accept()������ʼ�������ȴ��ͻ��˵�����
                Socket socket = serverSocket.accept();
                //��ȡ����������ȡ�ͻ��˷��͹�������Ϣ
                InputStream inputStream = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "gbk"));
                //��ȡ���������ͻ��˷�����Ϣ
                OutputStream outputStream = socket.getOutputStream();
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(outputStream, "gbk"));

                //��ȡ�ͻ��˷��͹�������Ϣ
                String recvStr = br.readLine();
                if (recvStr != null) {
                    //��recvStr���jsonת��Map����
                    Map<String, Object> recvMap = JSONObject.parseObject(recvStr, Map.class);

                    //����Ǹ�֪��⵽�������
                    if (recvMap.get("type").equals("productDetected")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                //��ʾ����ͼƬ���û�
                                productDetectController.setImageByPath("file:src/main/resources/image/positionDetected.jpg");
                                //���ü����ͼƬ�·�������Բ�����ɫΪ��ɫ
                                productDetectController.resetDefectCircleColor();
                            }
                        });
                    }
                    //����Ǹ�֪ץȡ����ͼƬ
                    else if (recvMap.get("type").equals("ImageCaught")) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                productDetectController.setImageByPath("file:src/main/resources/image/computing.jpg");
                            }
                        });
                    }
                    //��������ý����
                    else if (recvMap.get("type").equals("setDetectResult")) {
                        Map resultMap = (Map) recvMap.get("content");
                        Boolean isPinAskew = (Boolean) resultMap.get("isPinAskew");
                        Boolean isPinGlue = (Boolean) resultMap.get("isPinGlue");
                        Boolean isGlueOut = (Boolean) resultMap.get("isGlueOut");
                        String Base64Data = (String) resultMap.get("ImageResult");
                        //���ûص��������½���
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                productDetectController.setDetectResult(Base64Data, isPinAskew, isPinGlue, isGlueOut);
                            }
                        });
                    } else {
                        System.out.println("����δ����");
                    }
                }


                //�ر����������������Socket
                outputStream.close();
                inputStream.close();
                br.close();
                bw.close();
                socket.close();
                //severSocket�����ڴ˹رգ�����serverSocket.accept()�ᱨ��closed
                //serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setProductDetectController(ProductDetectController productDetectController) {
        this.productDetectController = productDetectController;
    }
}
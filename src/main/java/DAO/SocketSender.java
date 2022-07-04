package DAO;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 主要用于建立socket向本机指定端口发生消息
 * @author: 黄涛
 * @date: 2022-7-4
 */
public class SocketSender {
    public String send(String sendString, int port) {
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String retString = null;
        try {
            socket = new Socket("localhost", port);
            //获取输入输出流
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            //发送信息
            outputStream.write(sendString.getBytes());
            outputStream.flush();
            //等待服务器返回信息
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            retString = new String(bytes, 0, len);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                //关闭连接
                socket.close();
                //关闭输入输出流
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return retString;
        }
    }
}

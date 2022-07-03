package sample;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class PythonImageConnect {
    ServerSocket serverSocket = null;
    public static JSONObject image;
    public PythonImageConnect(){
        try {
            serverSocket = new ServerSocket(2580);
            System.out.println("等待连接");
            Socket accept = serverSocket.accept();
            InputStream is = accept.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String tmp = null;
            StringBuilder sb = new StringBuilder();
            while ((tmp = br.readLine()) != null)
                sb.append(tmp).append('\n');
            System.out.println("接收数据：" + sb.toString());
//             解析成对象
            image = JSONObject.parseObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

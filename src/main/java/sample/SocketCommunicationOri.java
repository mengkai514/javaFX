package sample;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketCommunicationOri {

     public static JSONObject res;

    public SocketCommunicationOri(String message){
        // 服务端IP地址
        String HOST = "192.168.3.100";
        // 服务端端口
        int PORT = 50001;

        Socket socket = null;
        {
            try {
                socket = new Socket(HOST, PORT);
                System.out.println("连接成功");
                ClientSend clientSend = new ClientSend(socket);
                clientSend.setMsg(message);
                new Thread(clientSend).start();
                ClientReceive clientReceive = new ClientReceive(socket);
                clientReceive.start();
                while (clientReceive.res == null){
                    try {
                        clientReceive.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                res = clientReceive.res;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientSend implements Runnable {
    private Socket socket;
    private String message;
    private JSONObject objectMessage;
    public ClientSend(Socket socket){
        this.socket = socket;
    }
    public void setMsg(String message) {
        this.message = message;
    }
    public void run() {
        this.sendMsg(message);
    }

    private void sendMsg(String message) {
        JSONObject jsonObject = new JSONObject();
        String str = "";
        if (!message.contains("type")){
            jsonObject.put("type",message);
            str = jsonObject.toJSONString();
        }
        else{
           str = message;
        }

//        jsonObject.put("content", message);

        System.out.println("发送内容给jsonobject" + str);
        try {
            // 获取输出流对象
            OutputStream os = socket.getOutputStream();
            PrintStream out = new PrintStream(os);
            // 发送内容
            out.print(str);
            // 告诉服务器内容发送完毕可以开始处理
            out.print("over");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class ClientReceive extends Thread{
    private Socket socket = null;
    public JSONObject res;
    public ClientReceive(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        res = this.receiveMsg();
    }

    private JSONObject receiveMsg() {
        System.out.println("开始接受");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(this.socket.getInputStream(), "utf-8"));
            String tmp = null;
            StringBuilder sb = new StringBuilder();
            System.out.println("读取内容");
//             读取内容
            while ((tmp = br.readLine()) != null)
                sb.append(tmp).append('\n');
            System.out.println("接收数据：" + sb.toString());
//             解析成对象
            JSONObject res = JSONObject.parseObject(sb.toString());
            return res;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
package sample;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class SocketCommunication {
    StringBuilder sb;
    public SocketCommunication(){}
    public String send(String sendString) {
        Socket socket = null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        String retString = null;
        String str = "";
        JSONObject jsonObject = new JSONObject();
        if (!sendString.contains("type")){
            jsonObject.put("type",sendString);
            str = jsonObject.toJSONString();
        } else {
            str = sendString;
        }
        int PORT = 50001;
        String HOST = "192.168.3.100";
        try {
            socket = new Socket(HOST, PORT);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            PrintStream out = new PrintStream(outputStream);
            out.print(str);
            out.print("over");
            out.flush();
            byte[] bytes = new byte[1024];
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            sb = new StringBuilder();
            String tmp = null;
            while((tmp = br.readLine()) != null)
                sb.append(tmp).append('\n');
//            int len = inputStream.read(bytes);
//            retString = new String(bytes, 0, len);
//            System.out.println(retString.charAt(retString.length()-1));
            System.out.println("接收数据"+sb.toString());
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                socket.close();
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return sb.toString();
        }
    }
}

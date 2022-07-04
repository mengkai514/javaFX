package DAO;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: ��Ҫ���ڽ���socket�򱾻�ָ���˿ڷ�����Ϣ
 * @author: ����
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
            //��ȡ���������
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            //������Ϣ
            outputStream.write(sendString.getBytes());
            outputStream.flush();
            //�ȴ�������������Ϣ
            byte[] bytes = new byte[1024];
            int len = inputStream.read(bytes);
            retString = new String(bytes, 0, len);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                //�ر�����
                socket.close();
                //�ر����������
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            return retString;
        }
    }
}

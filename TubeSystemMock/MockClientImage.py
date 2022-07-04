import base64
import json
import random
import socket
import time

flag = 0
while True:
    # 建立soket发送检测到晶体管信号
    if(flag%3 == 0):
        client = socket.socket()
        client.connect(('127.0.0.1', 8080))
        # 创建一个字典，存储返回给客户端的数据
        retDict = {"type": "productDetected"}
        retString = json.dumps(retDict)
        client.send(retString.encode('gbk'))
        print("检测到数码管，消息已发送")
        client.close()
        #暂停2秒
        time.sleep(0.5)
    elif(flag%3 == 1):
        client = socket.socket()
        client.connect(('127.0.0.1', 8080))
        # 创建一个字典，存储返回给客户端的数据
        retDict = {"type": "ImageCaught"}
        retString = json.dumps(retDict)
        client.send(retString.encode('gbk'))
        print("图片已经抓取，消息已发送")
        client.close()
        # 暂停2秒
        time.sleep(0.5)
    elif(flag%3 == 2):
        client = socket.socket()
        client.connect(('127.0.0.1', 8080))
        # 从本地加载图片并转换为base64编码
        with open("result.jpg", "rb") as f:
            # 将图片转换为base64编码
            imageBase64 = base64.b64encode(f.read())
            # 转换为字符串类型
            imageBase64 = imageBase64.decode("gbk")
        # 创建一个字典，存储返回给客户端的数据
        # 随机赋予status变量一个boolean值
        detectResult = {"isPinAskew": bool(random.randint(0, 1)), "isPinGlue": bool(random.randint(0, 1)), "isGlueOut": bool(random.randint(0, 1)), "ImageResult": imageBase64}
        retDict = {"type": "setDetectResult", "content": detectResult}
        retString = json.dumps(retDict)
        client.send(retString.encode('gbk'))
        print("已发送")
        client.close()
        # 暂停2秒
        time.sleep(1)
    flag = flag + 1

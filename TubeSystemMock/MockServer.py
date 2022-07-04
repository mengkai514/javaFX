import socket
# 建立soket并监听8080端口，等待连接

tcpserver = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcpserver.bind(("127.0.0.1", 9999))
tcpserver.listen(5)
while True:
    conn, addr = tcpserver.accept()
    print(conn)
    try:
        data = conn.recv(1024)
        recvString = data.decode("gbk")
        print("收到客户端消息："+recvString)
        # 将Json字符串解析为对象
        jsonObject = eval(recvString)

        # 如果接收到的是 启动 传送带的命令
        if jsonObject["type"] == "startConveyor":
            print("开始运行转运机")
            # 创建一个字典，存储返回给客户端的数据
            retDict = {"type": "retInfo", "statusCode": 200}
            # 将字典转换未Json字符串
            retString = str(retDict)
            conn.send(retString.encode("gbk"))

        # 如果接收到的是 停止 传送带的命令
        if jsonObject["type"] == "stopConveyor":
            print("停止转运机")
            # 创建一个字典，存储返回给客户端的数据
            retDict = {"type": "retInfo", "statusCode": 200}
            # 将字典转换未Json字符串
            retString = str(retDict)
            conn.send(retString.encode("gbk"))
    except Exception:
        break
    conn.close()
    print("连接关闭")

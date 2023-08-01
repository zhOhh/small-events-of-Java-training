package com.chatclient.service;

import com.chatcommon.Message;
import com.chatcommon.MessageType;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.management.MemoryType;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    private Socket socket;

    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println("客户端线程 等待");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //判断message类型 做相应业务处理
                //如果读到 服务器端返回在线用户列表
                if (message.getMesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)) {
                    //取出在线列表 并显示
                    String[] onlineUsers = message.getContent().split(" ");
                    System.out.println("\n=====当前在线用户列表======");
                    for (int i = 0; i < onlineUsers.length; i++) {
                        System.out.println("用户：" + onlineUsers[i]);
                    }
                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    //普通聊天信息
                    System.out.println("\n" + message.getSender() + " 对 " + message.getGetter() + " 说: " + message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    //群发信息
                    System.out.println("\n" + message.getSender() + " 对所有人说: " + message.getContent());
                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    System.out.println("\n"+message.getGetter()+ "给" + message.getGetter() + "发送了文件：" + message.getSrc()+"到我的电脑目录：" + message.getDest());

                    FileOutputStream fileOutputStream = new FileOutputStream(message.getDest());
                    fileOutputStream.write(message.getFileBytes());
                    fileOutputStream.close();
                    System.out.println("保存文件成功！");


                } else {
                    System.out.println("message 暂不处理...");
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }

    public Socket getSocket() {
        return socket;
    }
}

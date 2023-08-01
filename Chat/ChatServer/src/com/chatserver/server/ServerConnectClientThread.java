package com.chatserver.server;

import com.chatcommon.Message;
import com.chatcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

public class ServerConnectClientThread extends Thread {

    private Socket socket;
    private String userId;//连接服务端的用户id

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {

        while (true) {
            try {
                System.out.println(userId + "连接成功！");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();

                //根据message类型 做相应业务处理
                if (message.getMesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)) {
                    //客户端要在线用户列表
                    System.out.println(message.getSender() + "需要在线用户列表");
                    String onlineUser = MangerClientThreads.getOnlineUser();
                    //返回message
                    Message message2 = new Message();
                    message2.setMesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);
                    message2.setContent(onlineUser);
                    message2.setGetter(message.getSender());
                    //返回给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(message2);

                } else if (message.getMesType().equals(MessageType.MESSAGE_COMM_MES)) {
                    ServerConnectClientThread serverConnectClientThread = MangerClientThreads.getServerConnectClientThread(message.getGetter());
                    ObjectOutputStream oos = new ObjectOutputStream(serverConnectClientThread.getSocket().getOutputStream());
                    oos.writeObject(message);

                } else if (message.getMesType().equals(MessageType.MESSAGE_TO_ALL_MES)) {
                    //遍历 管理线程的集合
                    HashMap<String,ServerConnectClientThread> hm = MangerClientThreads.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while (iterator.hasNext()) {
                        //去除在线用户Id
                        String onLineUserId = iterator.next().toString();

                        if(!onLineUserId.equals(message.getSender())) {//排除群发消息的这个用户
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());

                            oos.writeObject(message);

                        }
                    }

                } else if (message.getMesType().equals(MessageType.MESSAGE_FILE_MES)) {
                    ObjectOutputStream oos = new ObjectOutputStream(MangerClientThreads.getServerConnectClientThread(message.getGetter()).getSocket().getOutputStream());
                    oos.writeObject(message);

                } else if (message.getMesType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    //客户端退出
                    System.out.println(message.getSender() + "退出了");
                     MangerClientThreads.removeServerConnectClientThread(message.getSender());
                    socket.close();//关闭连接
                    break;//退出线程

                } else {
                    System.out.println("message 暂不处理...");
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

    }
}

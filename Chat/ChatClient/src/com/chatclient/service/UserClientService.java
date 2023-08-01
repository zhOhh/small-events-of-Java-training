package com.chatclient.service;

import com.chatcommon.Message;
import com.chatcommon.MessageType;
import com.chatcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class UserClientService {

    private User u = new User();
    private Socket socket;

    public boolean checkUser(String userId, String pwd) {
        boolean b = false;

        u.setUserId(userId);
        u.setPasswd(pwd);

        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);//发送User对象

            //从服务器回复的Message对象
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            if (ms.getMesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {//登录成功
                //创建和服务器端保持通讯的线程
                ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                //启动线程
                clientConnectServerThread.start();
                //客户端扩展 将线程放到集合中管理
                MangerClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
                b = true;
            } else {//登录失败
                socket.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return b;

    }

    //向服务器端请求在线用户列表
    public void onlineFriendList() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);
        message.setSender(u.getUserId());

        //发送服务器
        try {
            //通过userId获得线程对象
            ClientConnectServerThread clientConnectServerThread = MangerClientConnectServerThread.getClientConnectServerThread(u.getUserId());
            //通过线程对象得到关联的socket
            Socket socket = clientConnectServerThread.getSocket();
            //得到当前Socket对应的PobjectOutputStream对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);//发送message对象  向服务器端请求在线用户列表
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //向服务器端发送退出系统message对象
    public void logout() {
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSender(u.getUserId());

        try {
            //ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream oos = new ObjectOutputStream(MangerClientConnectServerThread.getClientConnectServerThread(u.getUserId()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println(u.getUserId() + " 退出系统");
            System.exit(0);//结束进程
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}

package com.chatserver.server;

import com.chatcommon.Message;
import com.chatcommon.MessageType;
import com.chatcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    private ServerSocket ss = null;
    //创建集合存放多个用户
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();

    static {
        validUsers.put("100", new User("100", "123456"));
        validUsers.put("101", new User("101", "123456"));
        validUsers.put("102", new User("102", "123456"));
        validUsers.put("103", new User("103", "123456"));
        validUsers.put("104", new User("104", "123456"));
        validUsers.put("105", new User("105", "123456"));
    }

    //验证用户方法
    private boolean checkUser(String userId, String passwd) {
        User user = validUsers.get(userId);
        if (user == null) {// userId未被存到validUsers 的key中
            return false;
        }
        if (!user.getPasswd().equals(passwd)) {//userId正确passwd错误
            return false;
        }
        return true;
    }

    public ChatServer() {
        try {
            System.out.println("服务端 9999 端口 监听");
            new Thread(new SendNewsToAllService()).start();
            ss = new ServerSocket(9999);

            while (true) {//与某个客户端连接后 继续监听
                Socket socket = ss.accept();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                //得到socket关联的对象输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                User user = (User) ois.readObject();//读取客户端User对象
                //创建Message对象 准备回复客户端
                Message message = new Message();
                //验证
                if (checkUser(user.getUserId(), user.getPasswd())) {//登录通过
                    message.setMesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    //message对象回复
                    oos.writeObject(message);
                    //创建一个线程 和客户端保持通讯
                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, user.getUserId());
                    //启动线程
                    serverConnectClientThread.start();
                    //将线程对象添加到集合中
                    MangerClientThreads.addClientThread(user.getUserId(), serverConnectClientThread);
                } else { //登录失败
                    System.out.println(user.getUserId() + "登录失败");
                    message.setMesType(MessageType.MESSAGE_LOGIN_FAIL);
                    oos.writeObject(message);
                    socket.close();
                }


            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //退出while 说明服务器不在监听 关闭ServerSocket
            try {
                ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


    }

}

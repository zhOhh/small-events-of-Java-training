package com.chatclient.service;

import com.chatcommon.Message;
import com.chatcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class MessageClientService {

    /**
     * 发送一条消息给所有用户
     *
     * @param content  消息内容
     * @param senderId 发送用户ID
     */
    public void sendMessageToAll(String content, String senderId) {
        //构建Message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_TO_ALL_MES);//群发信息类型
        message.setSender(senderId);
        message.setContent(content);
        message.setSendTime(new Date().toString());//发送时间
        System.out.println(senderId + "对大家说" + content);
        //发送给服务端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(MangerClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 发送一条消息
     *
     * @param content  消息内容
     * @param senderId 发送用户ID
     * @param getterId 接收用户ID
     */
    public void sendMessageToOne(String content, String senderId, String getterId) {
        //构建Message
        Message message = new Message();
        message.setMesType(MessageType.MESSAGE_COMM_MES);//普通聊天信息类型
        message.setSender(senderId);
        message.setGetter(getterId);
        message.setContent(content);
        message.setSendTime(new Date().toString());//发送时间
        System.out.println(senderId + "对" + getterId + "说" + content);
        //发送给服务端
        try {
            ObjectOutputStream oos = new ObjectOutputStream(MangerClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

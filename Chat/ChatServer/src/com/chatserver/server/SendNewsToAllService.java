package com.chatserver.server;

import com.chatcommon.Message;
import com.chatcommon.MessageType;
import com.utils.Utility;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

public class SendNewsToAllService implements Runnable {

    @Override
    public void run() {
        while (true) {
            System.out.println("请输入服务器要发送的消息[输入exit退出推送服务]：");
            String news = Utility.readString(100);
            if ("exit".equals(news)) {
                break;
            }
            Message message = new Message();
            message.setSender("服务器");
            message.setMesType(MessageType.MESSAGE_TO_ALL_MES);
            message.setContent(news);
            message.setSendTime(new Date().toString());
            System.out.println("服务器发送消息给所有人：" + news);

            HashMap<String, ServerConnectClientThread> hm = MangerClientThreads.getHm();

            Iterator<String> iterator = hm.keySet().iterator();
            while (iterator.hasNext()) {
                String onLineUserId = iterator.next().toString();
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(hm.get(onLineUserId).getSocket().getOutputStream());
                    oos.writeObject(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}

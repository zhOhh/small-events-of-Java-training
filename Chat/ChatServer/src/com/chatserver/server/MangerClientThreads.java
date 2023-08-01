package com.chatserver.server;

import java.util.HashMap;
import java.util.Iterator;

public class MangerClientThreads {
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();


    public static HashMap<String, ServerConnectClientThread> getHm() {
        return hm;
    }

    //添加线程对象
    public static void addClientThread(String userId, ServerConnectClientThread serverConnectClientThread) {

        hm.put(userId, serverConnectClientThread);

    }

    // 根据userId 获取线程对象
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }

    //从集合中移除线程对象
    public static void removeServerConnectClientThread(String userId) {
        hm.remove(userId);
    }

    //返回在线用户列表
    public static String getOnlineUser() {
        //遍历hashMap的key
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        while (iterator.hasNext()) {
            onlineUserList += iterator.next().toString() + " ";
        }
        return onlineUserList;
    }
}

package com.chatclient.service;

import java.util.HashMap;

public class MangerClientConnectServerThread {
    //将多个线程放入一个HashMap集合 key 是用户id value是线程
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    //将线程加入到集合
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread clientConnectServerThread) {
        hm.put(userId, clientConnectServerThread);
    }

    //通过userId可获得对应线程
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }


}

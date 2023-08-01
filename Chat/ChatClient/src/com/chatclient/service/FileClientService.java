package com.chatclient.service;

import com.chatcommon.Message;
import com.chatcommon.MessageType;

import java.io.*;

public class FileClientService {

    /**
     *
     * @param src 源文件
     * @param dest 发送到对方那个目录
     * @param senderId 发送者id
     * @param getterId 接收者id
     */
   public void sendFileToOne(String src,String dest, String senderId, String getterId) {

       Message message = new Message();
       message.setMesType(MessageType.MESSAGE_FILE_MES);
       message.setSender(senderId);
       message.setGetter(getterId);
       message.setDest(dest);
       message.setSrc(src);

       FileInputStream fileInputStream=null;
       byte[] fileBytes = new byte[(int) new File(src).length()];

       try {
           fileInputStream = new FileInputStream(src);
           fileInputStream.read(fileBytes);
           message.setFileBytes(fileBytes);
       } catch (Exception e) {
           throw new RuntimeException(e);
       } finally {
           if (fileInputStream!=null){
               try {
                   fileInputStream.close();
               } catch (IOException e) {
                   throw new RuntimeException(e);
               }
           }
       }

       System.out.println("\n"+senderId+"给"+getterId+"发送文件: "+src+" 到对方的电脑目录 "+dest);

       try {
           ObjectOutputStream oos = new ObjectOutputStream(MangerClientConnectServerThread.getClientConnectServerThread(senderId).getSocket().getOutputStream());
            oos.writeObject(message);
       } catch (IOException e) {
           throw new RuntimeException(e);
       }

   }

}

package com.chatclient.view;

import com.chatclient.service.FileClientService;
import com.chatclient.service.MessageClientService;
import com.chatclient.service.UserClientService;
import com.chatclient.utils.Utility;

public class ChatView {
    private boolean loop = true; //是否显示菜单
    private String key = ""; //接收用户键入
    private UserClientService userClientService = new UserClientService();//用于登录/注册
    private MessageClientService messageClientService = new MessageClientService();//私聊/群聊
    private FileClientService fileClientService = new FileClientService();//发送文件


    public static void main(String[] args) {
        new ChatView().mainMenu();
        System.out.println("客户端退出系统...");
    }

    //主菜单
    private void mainMenu() {
        while (loop) {
            System.out.println("==========欢迎登录网络通讯系统==========");
            System.out.println("\t\t 1 登录系统 ");
            System.out.println("\t\t 9 退出系统 ");
            System.out.println("请输入您的选择:");

            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("请输入用户账号");
                    String userId = Utility.readString(50);
                    System.out.println("请输入密码");
                    String pwd = Utility.readString(50);

                    if (userClientService.checkUser(userId, pwd)) {
                        System.out.println("==========欢迎" + userId + "登录成功==========");
                        //进入二级菜单
                        while (loop) {
                            System.out.println("\n==========网络通信系统二级菜单(用户" + userId + ")==========");
                            System.out.println("\t\t 1 显示在线用户列表 ");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.println("请输入您的选择:");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    //显示在线用户列表
                                    userClientService.onlineFriendList();
                                    break;
                                case "2":
                                    //群发消息
                                    System.out.println("请输入想要对大家说的话:");
                                    String s = Utility.readString(100);
                                    //将消息发送给服务端
                                    messageClientService.sendMessageToAll(s, userId);
                                    break;
                                case "3":
                                    //私聊消息
                                    System.out.println("请输入想聊天的用户名(在线):");
                                    String getterId = Utility.readString(50);
                                    System.out.println("请输入想说的话:");
                                    String content = Utility.readString(100);
                                    //将消息发送给服务端
                                    messageClientService.sendMessageToOne(content, userId, getterId);
                                    break;
                                case "4":
                                    //发送文件
                                    System.out.print("请输入要发送的用户（在线）:");
                                    getterId = Utility.readString(50);
                                    System.out.print("请输入要发送的文件路径(如: d:\\xx.jpg):");
                                    String src = Utility.readString(100);
                                    System.out.print("请输入要发送到对方的文件路径(如: d:\\xx.jpg):");
                                    String dest = Utility.readString(100);
                                    fileClientService.sendFileToOne(src, dest, userId, getterId);
                                    break;
                                case "9":
                                    userClientService.logout();
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("输入错误");
                                    break;
                            }

                        }
                    } else {
                        System.out.println("=====登录失败=====");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
                default:
                    System.out.println("输入错误");
                    break;
            }

        }
    }

}

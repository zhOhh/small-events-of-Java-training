package TankGame6;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

public class Game_06 extends JFrame {

    MyPanel mp = null;
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Game_06 game01 = new Game_06();
    }

    public Game_06() {
        System.out.println("请输入选择 1:新游戏 2:继续上局");
        String key=scanner.next();
        mp = new MyPanel(key);
        //将mp放入Thread 启动
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//面板

        this.setSize(1300, 950);
        this.addKeyListener(mp);//JFrame 监听mp键盘事件
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });
    }
}

package TankGame1;

import javax.swing.*;

public class Game_01 extends JFrame {

    MyPanel mp = null;

    public static void main(String[] args) {
        Game_01 game01 = new Game_01();
    }

    public Game_01() {
        mp = new MyPanel();
        this.add(mp);
        this.setSize(1000, 750);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

    }
}

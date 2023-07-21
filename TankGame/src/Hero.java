package TankGame6;

import java.util.Vector;

public class Hero extends Tank {
    //定义shot对象  射击（线程）
    Shot shot = null;
    Vector<Shot> shots = new Vector<>();

    public Hero(int x, int y) {
        super(x, y);
    }

    //射击
    public void shotEnemyTank() {

        if (shots.size()==5){
            return;
        }

        //创建shot对象
        switch (getDirect()) {//Hero对象的方向
            case 0://上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1://右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2://下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3://左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        shots.add(shot);
        //启动Shot线程
        new Thread(shot).start();


    }
}

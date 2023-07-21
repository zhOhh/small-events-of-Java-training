package TankGame6;

import java.util.Vector;

@SuppressWarnings({"all"})
public class EnemyTank extends TankGame6.Tank implements Runnable {
    //敌人坦克类中 使用 Vector 保存多个Shot
    Vector<TankGame6.Shot> shots = new Vector<>();
    Vector<EnemyTank> enemyTanks = new Vector<>();
    boolean isLive = true;

    public EnemyTank(int x, int y) {
        super(x, y);
    }

    public void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //判断当前敌人坦克是否和其他坦克重叠
    public boolean isTouchEnemyTank() {
        //当前敌人坦克方向
        switch (this.getDirect()) {
            case 0://上
                //敌人坦克和其他敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下
                        // x的范围 [enemyTank.getX(),enemyTank.getX() + 40]
                        // y的范围 [enemyTank.getY(),enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 左上角坐标 [this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克 右上角的坐标 [this.getX() + 40,this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是右/左
                        // x的范围 [enemyTank.getX(),enemyTank.getX() + 60]
                        // y的范围 [enemyTank.getY(),enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 左上角坐标 [this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克 右上角的坐标 [this.getX() + 40,this.getY()]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://右
                //敌人坦克和其他敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下
                        // x的范围 [enemyTank.getX(),enemyTank.getX() + 40]
                        // y的范围 [enemyTank.getY(),enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 右上角坐标 [this.getX() + 60,this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克 右下角的坐标 [this.getX() + 60,this.getY()+40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是右/左
                        // x的范围 [enemyTank.getX(),enemyTank.getX() + 60]
                        // y的范围 [enemyTank.getY(),enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 右上角坐标 [this.getX() +60,this.getY()]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克 右下角的坐标 [this.getX() + 60,this.getY()+40]
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://下
                //敌人坦克和其他敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下
                        // x的范围 [enemyTank.getX(),enemyTank.getX() + 40]
                        // y的范围 [enemyTank.getY(),enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 左下角坐标 [this.getX(),this.getY() +60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克 右下角的坐标 [this.getX() + 40,this.getY() +60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是右/左
                        // x的范围 [enemyTank.getX(),enemyTank.getX() + 60]
                        // y的范围 [enemyTank.getY(),enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 左下角坐标 [this.getX(),this.getY() +60]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克 右下角的坐标 [this.getX() + 40,this.getY() +60]
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://左
                //敌人坦克和其他敌人坦克比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    //不和自己比
                    if (enemyTank != this) {
                        //如果敌人坦克是上/下
                        // x的范围 [enemyTank.getX(),enemyTank.getX() + 40]
                        // y的范围 [enemyTank.getY(),enemyTank.getY() + 60]
                        if (enemyTank.getDirect() == 0 || enemyTank.getDirect() == 2) {
                            //当前坦克 左上角坐标 [this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克 左下角的坐标 [this.getX() ,this.getY() +40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        //如果敌人坦克是右/左
                        // x的范围 [enemyTank.getX(),enemyTank.getX() + 60]
                        // y的范围 [enemyTank.getY(),enemyTank.getY() + 40]
                        if (enemyTank.getDirect() == 1 || enemyTank.getDirect() == 3) {
                            //当前坦克 左上角坐标 [this.getX(),this.getY()]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克 左下角的坐标 [this.getX() ,this.getY() + 40]
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public void run() {
        while (true) {
            if (isLive && shots.size() < 1) {
                TankGame6.Shot s = null;
                switch (getDirect()) {
                    case 0:
                        s = new TankGame6.Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        s = new TankGame6.Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        s = new TankGame6.Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        s = new TankGame6.Shot(getX(), getY() + 20, 3);
                        break;
                }

                shots.add(s);
                new Thread(s).start();

            }

            switch (getDirect()) {
                case 0: //向上
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0 && !isTouchEnemyTank()) {
                            moveUp();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 1: //向右
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
                            moveRight();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 2: //向下
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {
                            moveDown();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case 3: //向左
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0 && !isTouchEnemyTank()) {
                            moveLeft();
                        }
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
            }


            setDirect((int) (Math.random() * 4));

            if (!isLive) {
                break;
            }

        }
    }
}

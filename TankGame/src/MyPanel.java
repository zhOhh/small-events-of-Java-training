package TankGame6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Vector;

//监听 键盘事件  实现KeyListener
//Panel不停重绘子弹 需将MyPanel实现Runnable 当作一个线程使用
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义自己坦克
    Hero hero = null;
    //定义敌人坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义Node 对象的 Vector 用于恢复敌人的坦克坐标和方向
    Vector<Node> nodes=new Vector<>();
    //定义存放炸弹
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 3;

    //定义三张炸弹图片 显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public MyPanel(String key) {
        File file = new File(Recorder.getRecordFile());
        if (file.exists()) {
            nodes = Recorder.getNodesAndEnemyTankRec();
        }else {
            System.out.println("文件不存在，只能开启新游戏");
            key="1";
        }
        Recorder.setEnemyTanks(enemyTanks);
        hero = new Hero(500, 100);//初始化自己坦克

        switch (key) {
            case "1"://新游戏
                //初始化敌人坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    //创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //将enemyTanks设置给enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(2);
                    //启动
                    new Thread(enemyTank).start();
                    //给enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);

                }
                break;
            case "2"://继续上局
                //初始化敌人坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    //创建一个敌人坦克
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将enemyTanks设置给enemyTank
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置方向
                    enemyTank.setDirect(node.getDirect());
                    //启动
                    new Thread(enemyTank).start();
                    //给enemyTank加入一颗子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirect());
                    //加入enemyTank的Vector成员
                    enemyTank.shots.add(shot);
                    //启动shot对象
                    new Thread(shot).start();
                    //加入
                    enemyTanks.add(enemyTank);

                }
                break;
            default:
                System.out.println("输入有误");
                break;
        }


        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }

    //显示我方击毁坦克数量
    public void showInfo(Graphics g) {
        //画出玩家总成绩
        g.setColor(Color.BLACK);
        g.setFont(new Font("宋体", Font.BOLD, 20));

        g.drawString("累计击毁敌方坦克:", 1020, 30);
        drawTank(1020, 60, g, 0, 0);
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 100);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//填充矩形
        showInfo(g);
        if (hero != null && hero.isLive) {
            //画出自己的坦克
            drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 1);
        }

        //画出hero子弹
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            if (shot != null && shot.isLive) {
                g.draw3DRect(shot.x, shot.y, 1, 1, false);
            } else {
                hero.shots.remove(shot);
            }
        }


        //如果bombs集合中有对就画出
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            //根据bomb对象的life值去画对应图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //让炸弹生命值减少
            bomb.lifeDown();
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        //画出敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);

            if (enemyTank.isLive) {//敌人坦克存活
                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 0);
                //画出enemyTanks的所有子弹
                for (int j = 0; j < enemyTank.shots.size(); j++) {
                    Shot shot = enemyTank.shots.get(j);
                    if (shot.isLive) {
                        g.draw3DRect(shot.x, shot.y, 1, 1, false);
                    } else {
                        enemyTank.shots.remove(shot);
                    }
                }
            }
        }
    }


    public void drawTank(int x, int y, Graphics g, int direct, int type) {
        switch (type) {
            case 0:
                g.setColor(Color.cyan);
                break;
            case 1:
                g.setColor(Color.yellow);
        }

        switch (direct) {
            case 0:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y);
                break;
            case 1:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2:
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3:
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
            default:
                System.out.println("无。。。。。");
        }

    }

    public void hitEnemyTank() {

        for (int j = 0; j < hero.shots.size(); j++) {
            Shot shot = hero.shots.get(j);
            //判断是否击中敌人坦克
            if (shot != null && hero.shot.isLive) {
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    hitTank(hero.shot, enemyTank);

                }
            }
        }

    }

    //是否击中我方
    public void hitHero() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            for (int j = 0; j < enemyTank.shots.size(); j++) {
                Shot shot = enemyTank.shots.get(j);
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    //是否击中敌方
    public void hitTank(Shot s, Tank enemyTank) {
        switch (enemyTank.getDirect()) {
            case 0://坦克向上
            case 2://坦克向下
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 40
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 60) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //Bomb对象
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1://坦克向右
            case 3://坦克向左
                if (s.x > enemyTank.getX() && s.x < enemyTank.getX() + 60
                        && s.y > enemyTank.getY() && s.y < enemyTank.getY() + 40) {
                    s.isLive = false;
                    enemyTank.isLive = false;
                    enemyTanks.remove(enemyTank);
                    if (enemyTank instanceof EnemyTank) {
                        Recorder.addAllEnemyTankNum();
                    }
                    //Bomb对象
                    Bomb bomb = new Bomb(enemyTank.getX(), enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理wasd键按下
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDirect(0);
            if (hero.getY() > 0) {
                hero.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            hero.setDirect(1);
            if (hero.getX() + 60 < 1000) {
                hero.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDirect(2);
            if (hero.getY() + 60 < 750) {
                hero.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            hero.setDirect(3);
            if (hero.getX() > 0) {
                hero.moveLeft();
            }
        }

        //用户按J 发射
        if (e.getKeyCode() == KeyEvent.VK_J) {
            hero.shotEnemyTank();
        }
        //重绘
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//每个100毫秒 重绘
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            hitEnemyTank();
            hitHero();
            this.repaint();
        }

    }
}

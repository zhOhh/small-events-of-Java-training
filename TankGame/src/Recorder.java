package TankGame6;

import java.io.*;
import java.util.Vector;

@SuppressWarnings({"all"})
public class Recorder {
    //击毁敌方坦克数
    private static int allEnemyTankNum = 0;
    //定义IO对象 写数据到文件中
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "src\\myRecord.txt";
    //定义Vector 指向Mypanel对象的敌人坦克Vector
    public static Vector<EnemyTank> enemyTanks = null;
    //定义Vector  保存敌人信息的node
    public static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static String getRecordFile(){
        return recordFile;
    }

    //读取recordFile 恢复相关信息
    //该方法在继续上局时使用
    public static Vector<Node> getNodesAndEnemyTankRec() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            allEnemyTankNum = Integer.parseInt(br.readLine());
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(
                        Integer.parseInt(xyd[0]),
                        Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2])
                );
                nodes.add(node);//放入 nodes Vector中
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return nodes;
    }

    //当游戏退出 将allEnemyTankNum 保存到 recordFile中
    public static void keepRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(recordFile));
            bw.write(allEnemyTankNum + "\r\n");
            //遍历敌人Vector
            for (int i = 0; i < enemyTanks.size(); i++) {
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    bw.write(record + "\r\n");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //当我方击毁敌方坦克 allEnemyTankNum++
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }

}

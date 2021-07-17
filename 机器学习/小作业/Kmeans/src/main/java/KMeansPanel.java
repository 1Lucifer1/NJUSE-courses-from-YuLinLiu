import java.awt.*;
import javax.swing.JPanel;
import java.util.Random;


class KMeansPanel extends JPanel {
    //中心个数
    int K = 5;
    //点的个数
    int Nodes = 100;
    //二维
    int Dimension = 2;
    //迭代次数
    int range = 10000;

    Random rand = new Random();


    class Point {
        Point() {
            initial();
        }

        void initial() {
            /*初始化为[0,600)的随机点，簇编号为-1，无意义*/
            for (int i = 0; i < Dimension; ++i)
                x[i] = rand.nextDouble() * 600;
            cluster = -1;
        }

        //点的坐标，二维
        double x[] = new double[Dimension];
        //点的簇编号
        int cluster;
    }

    ;


    //所有数据
    Point p[];
    //这一次的中心点坐标
    Point newCluster[];
    //上一次的中心坐标
    Point oldCluster[];
    //表示不同簇的颜色值
    Color colors[];

    /*欧式距离*/
    double Euc(Point p1, Point p2) {
        double distance = 0.0;

        for (int i = 0; i < Dimension; ++i)
            distance += (p1.x[i] - p2.x[i]) * (p1.x[i] - p2.x[i]);
        return Math.sqrt(distance);
    }

    /*更新中心点*/
    void updateCentroid(int clusterNum) {
        //将newCluster数组的那个中心点置空
        for (int i = 0; i < Dimension; ++i)
            newCluster[clusterNum].x[i] = 0;

        int clusterSize = 0;

        for (int i = 0; i < Nodes; ++i)
            if (p[i].cluster == clusterNum) {
                //这个簇中有多少点
                clusterSize++;
                for (int j = 0; j < Dimension; ++j)
                    newCluster[clusterNum].x[j] += p[i].x[j];
            }


        if (clusterSize == 0)
            return;

        for (int i = 0; i < Dimension; ++i)
            newCluster[clusterNum].x[i] /= (double) clusterSize;
    }


    /*更新中心点的接口函数*/
    void updateCentroids() {
        for (int i = 0; i < K; ++i)
            updateCentroid(i);
    }

    /*分配数据点到哪个簇*/
    void assign() {
        for (int i = 0; i < Nodes; ++i)
            assignPoint(i);
    }

    /*分配数据点到哪个簇*/
    void assignPoint(int x) {
        double minDistance = 99999999;
        int nodeClassify = 1;
        for (int i = 0; i < K; ++i) {
            //计算欧式距离
            double newDistance = Euc(p[x], newCluster[i]);
            if (newDistance < minDistance) {
                minDistance = newDistance;
                nodeClassify = i;
            }
        }
        p[x].cluster = nodeClassify;
    }


    /*判断2点是否同一个点*/
    Boolean samePoint(Point p1, Point p2) {
        if (p1.cluster != p2.cluster)
            return false;
        for (int i = 0; i < Dimension; ++i)
            if (p1.x[i] != p2.x[i])
                return false;
        return true;
    }


    /*判断算法是否终止*/
    Boolean stop(int currentTime) {
        //超过迭代次数
        if (currentTime > range) {
            int num = 1;
            System.out.println("超过迭代次数");
            for (Point i : oldCluster) {
                System.out.println("中心点" + num + "坐标：(" + i.x[0] + "," + i.x[1] + ")");
                num++;
            }
            return true;
        }
        /*如果每一个中心点都与上一次的中心点相同，则算法终止，否则更新oldCentroid*/
        for (int i = 0; i < K; ++i)
            if (!samePoint(oldCluster[i], newCluster[i])) {
                for (int j = 0; j < K; ++j)
                    copy(oldCluster[j], newCluster[j]);
                return false;
            }
        int num = 1;
        System.out.println("迭代完成");
        for (Point i : oldCluster) {
            System.out.println("中心点" + num + "坐标：(" + i.x[0] + "," + i.x[1] + ")");
            num++;
        }
        return true;
    }


    /*令p1 = p2*/
    void copy(Point p1, Point p2) {
        p1.cluster = p2.cluster;
        for (int i = 0; i < Dimension; ++i)
            p1.x[i] = p2.x[i];
    }

    /*初始化*/
    void initial() {
        //初始化所有点的数组，中心点数组和颜色数组
        p = new Point[Nodes];
        newCluster = new Point[K];
        oldCluster = new Point[K];
        colors = new Color[K];

        //初始化每个点
        for (int i = 0; i < Nodes; ++i) {
            p[i] = new Point();
            //初始化点，也就是随机生成点的坐标，在point类中可以看到这个方法
            p[i].initial();
        }

        //生成k个中心
        for (int i = 0; i < K; ++i) {
            newCluster[i] = new Point();
            oldCluster[i] = new Point();
            newCluster[i].initial();
            oldCluster[i].initial();
            copy(oldCluster[i], newCluster[i]);
            colors[i] = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        }
    }


    /*默认构造函数，调用初始化函数*/
    KMeansPanel() {
        initial();
    }


    /*重载绘图函数*/
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        setBackground(Color.white);
        /*画数据点（圆形），根据簇编号来确定颜色*/
        for (int i = 0; i < Nodes; ++i) {
            int x = (int) p[i].x[0], y = (int) p[i].x[1];


            if (p[i].cluster == -1)
                g.setColor(Color.black);
            else
                g.setColor(colors[p[i].cluster]);


            g.fillOval(x, y, 15, 15);
        }
        /*画中心点（矩形），根据簇编号来确定颜色*/
        for (int i = 0; i < K; ++i) {
            int x = (int) newCluster[i].x[0], y = (int) newCluster[i].x[1];


            g.setColor(colors[i]);


            g.fillRect(x, y, 15, 15);
        }
    }
}
package Test;

import java.io.*;

public class Test {
    private static int input=4;//输入节点个数
    private static int hide=10;//隐藏节点个数
    private static int output=3;//输出层节点个数
    private static int trainTimes=75;//训练次数
    private static int testTimes=75;//测试次数
    private static double[][] trainNum=new double[trainTimes][input+1];//训练数据，加一是因为多了结果一列
    private static double[][]  testNum=new double[testTimes][input+1];//测试数据
    private static double[][] W=new double[input][hide];//存放输入层到隐藏层的权值
    private static double[][] V=new double[hide][output];//存放隐藏层到输出层的权值
    private static double[] hideVal=new double[hide];//存放隐藏层阈值
    private static double[] outVal=new double[output];//存放输出层阈值
    private static double[][] object=new double[trainTimes][output];//目标状态集
    private static double[] max=new double[input];//存放四个特性的最大值
    private static double[] min=new double[input];//存放四个特性的最小值
    private static double[] outRightRate=new double[testTimes];
    private static double fang=0;

    static {//从文件读取数据(数据初始化)
        try {//初始话训练数据
            BufferedReader bfTrain=new BufferedReader(new FileReader(new File("D:\\java_code\\bp_nerve\\src\\Test\\train.txt")));
            for (int i = 0; i < trainTimes; i++) {
                String line = bfTrain.readLine();
                String[] lineData = line.split("\\s+");//注意是\t不是\n
                for (int j = 0; j < 5; j++) {
                    trainNum[i][j]=Double.parseDouble(lineData[j]);
                }
            }
            findMaxAndMin();
            //归一化输入数据
            for (int i = 0; i < trainTimes; i++) {
                for (int j = 0; j < input; j++) {
                    trainNum[i][j]=prime(trainNum[i][j],max[j],min[j]);
                }
            }

            ////初始化测试数据
            BufferedReader bfTest=new BufferedReader(new FileReader(new File("D:\\java_code\\bp_nerve\\src\\Test\\test.txt")));
            for (int i = 0; i < testTimes; i++) {
                String line = bfTest.readLine();
                String[] lineData = line.split("\\s+");
                for (int j = 0; j < 5; j++) {
                    testNum[i][j]=Double.parseDouble(lineData[j]);
                }
            }
            //归一化测试数据
            for (int i = 0; i < testTimes; i++) {
                for (int j = 0; j < input; j++) {
                    testNum[i][j]=prime(testNum[i][j],max[j],min[j]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 


        //初始化隐藏层阈值,全为0
        for (int i = 0; i < hide; i++) {
            hideVal[i]=0;
        }

        //初始化输出层阈值，全为0
        for (int i = 0; i < output; i++) {
            outVal[i]=0;
        }

        //初始化输入层到隐藏层的权值，随机数
        for (int i = 0; i < input; i++) {
            for (int j = 0; j < hide; j++) {
                W[i][j]=Math.random();
            }
        }

        //初始化隐藏层到输出层的权值，随机数
        for (int i = 0; i < hide; i++) {
            for (int j = 0; j < output; j++) {
                V[i][j]=Math.random();
            }
        }
        //初始化目标状态
        for (int i = 0; i < trainTimes; i++) {
            for (int j = 0; j < output; j++) {
                object[i][j]=0;
            }
            int a = (int)trainNum[i][4];
            object[i][a-1]=1;
        }
    }

    public static void findMaxAndMin(){//找到四个特性中的每个特性的取值范围（最小-最大）
        for (int i = 0; i < input; i++) {
            max[i]=Double.MIN_VALUE;
            min[i]=Double.MAX_VALUE;
        }
        for (int i = 0; i < trainTimes; i++) {
            for (int j = 0; j < input; j++) {
                if(trainNum[i][j]>max[j]){
                    max[j]=trainNum[i][j];
                }
                if(trainNum[i][j]<min[j]){
                    min[j]=trainNum[i][j];
                }
            }
        }
    }

    /**
     * 下一步将输入根据max和min归一化
     * @param out
     * @return
     */

    public static double[] primeOut(double[] out){//输出归一化
        double sum=0;

        for (int i = 0; i < out.length; i++) {
            sum+=out[i];
        }
        for (int i = 0; i < out.length; i++) {
            out[i]=out[i]/sum;
        }
        return out;
    }

    public static double prime(double num,double max,double min){//数据归一化
        if(num>max){
            num=max;
        }
        if(num<min){
            num=min;
        }
        return 2*(num-min)/(max-min)-1;
    }

    public static void train(){
        int times=0;
        double rate_w=0.9;//输入层到隐层的学习率
        double rate_w1=0.9;//隐层到输出层的学习率
        double rate_b1=0.9;//隐层阈值学习率
        double rate_b2=0.9;//输出层阈值学习率
        double[] h=new double[hide];//隐层节点的激活值
        double[] o=new double[output];//输出节点的激活值
        double[] h1=new double[hide];//隐层向输出层的输入
        double[] o1=new double[output];//输出节点的输出
        double[] qq=new double[output];//输出节点的期望值与实际值偏差
        double[] pp=new double[hide];//隐层节点校正误差

        while(times<600*trainTimes){//训练次数不够，则继续训练
            //计算隐藏层的各节点激活值和输出值
            for (int i = 0; i < hide; i++) {
                h[i]=0;
                for (int j = 0; j < input; j++) {
                    h[i]=h[i]+W[j][i]*trainNum[times%75][j];//隐藏层激活值
                }
                h1[i]=1.0/(1.0+Math.exp(-h[i]-hideVal[i]));//隐藏层向输出层的输入
            }
            //计算输出层各节点激活值和输出值
            for (int i = 0; i < output; i++) {
                o[i]=0;
                for (int j = 0; j < hide; j++) {
                    o[i]=o[i]+V[j][i]*h1[j];//输出层激活值
                }
                o1[i]=1.0/(1.0+Math.exp(-o[i]-outVal[i]));//输出层各节点的输出
            }

//            for (int j = 0; j < o1.length; j++) {
//                System.out.print(times+"  "+o1[j]+"  ");
//            }
//            System.out.println(".................");
            //计算实际输出与期望输出的偏差qq，反向调节隐层到输出层的路径上的权值
            for (int i = 0; i < output; i++) {
                o1=primeOut(o1);//输出结果归一化
                qq[i]=(object[times%75][i]-o1[i])*o1[i]*(1-o1[i]);
            }

            for (int i = 0; i < hide; i++) {
                pp[i]=0;
                for (int j = 0; j < output; j++) {
                    pp[i]=pp[i]+qq[j]*V[i][j];//未改变V之前，先计算pp
                }
                pp[i] = pp[i]*h1[i]*(1.0-h1[i]);
            }

            for (int i = 0; i < output; i++) {
                for (int j = 0; j < hide; j++) {
                    V[j][i]=V[j][i]+rate_w1*qq[i]*h1[j];//在更新V
                }
            }
            //继续反向传播调整输入层到隐层的各路径上的权值
            for (int i = 0; i < hide; i++) {
                for (int j = 0; j < input; j++) {
                    W[j][i]=W[j][i]+rate_w*pp[i]*trainNum[times%75][j];
                }
            }
            //调整输出各节点的阈值
            for (int i = 0; i < output; i++) {
                outVal[i]=outVal[i]+rate_b2*qq[i];
            }
            //调整隐藏层各节点的阈值
            for (int i = 0; i < hide; i++) {
                hideVal[i]=hideVal[i]+rate_b1*pp[i];
            }

            times++;
        }
    }


    public static double test(){//测试代码
        double[] ht=new double[hide];//存放隐层的激活值o
        double[] hp=new double[hide];//存放隐层像输出层的输入
        double[] ot=new double[output];//存放输出层的激活值
        double[] op=new double[output];//存放输出层的输出值
        int times=0;
        double sum=0;
        double temp;
        while(times<testTimes){
            for (int i = 0; i < hide; i++) {//隐藏层激活值和输出值
                ht[i]=0;
                hp[i]=0;
                for (int j = 0; j < input; j++) {
                    ht[i]=ht[i]+W[j][i]*testNum[times][j];
                }
                hp[i]=1.0/(1.0+Math.exp(-ht[i]-hideVal[i]));
            }
            for (int i = 0; i < output; i++) {
                ot[i]=0;
                op[i]=0;
                for (int j = 0; j < hide; j++) {
                    ot[i]=ot[i]+V[j][i]*hp[j];
                }
                op[i]=1.0/(1.0+Math.exp(-ot[i]-outVal[i]));
//                System.out.print(times+"  "+op[i]+"   ");
            }
//            System.out.println();
            System.out.println("第"+times+"测试");
//            for (int i = 0; i < 3; i++) {
//                System.out.print(op[i]+"  ");
//            }
//            System.out.println();
            double[] out = primeOut(op);
            for (int i = 0; i < 3; i++) {
                System.out.print(out[i]+"   ");
            }
            System.out.println();
            if(times<25){
                temp=out[0];
            }else if(times<50){
                temp=out[1];
            }else{
                temp=out[2];
            }
            sum+=temp;
            outRightRate[times]=temp;
            times++;
        }
        double rate=sum/75;
        for (int i = 0; i < testTimes; i++) {
            fang+=Math.pow((outRightRate[i]-rate),2);
        }
        fang=Math.sqrt(fang/75);
        return rate;
    }

    public static void main(String[] args) {
        train();
        System.out.println("平均准确率为："+test());
        System.out.println("标准差为:"+fang);
    }
}

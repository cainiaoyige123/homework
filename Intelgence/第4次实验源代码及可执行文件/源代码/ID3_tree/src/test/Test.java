package test;

import javax.tools.Tool;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Test {
    private static List<Flower> trainList=new ArrayList<>();
    private static List<Flower> testList=new ArrayList<>();

    static {
        //初始化训练数据
        BufferedReader bf= null;
        try {
            bf = new BufferedReader(new FileReader(new File("D:\\java_code\\ID3_tree\\src\\test\\traindata.txt")));
            for (int i = 0; i < 75; i++) {
                String line = bf.readLine();
                String[] nums = line.split("\\s+");
                Flower flower=new Flower(Double.parseDouble(nums[0]),Double.parseDouble(nums[1]),Double.parseDouble(nums[2]),Double.parseDouble(nums[3]),Integer.parseInt(nums[4]));
                trainList.add(flower);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //初始化测试数据
        BufferedReader bf1= null;
        try {
            bf1 = new BufferedReader(new FileReader(new File("D:\\java_code\\ID3_tree\\src\\test\\testdata.txt")));
            for (int i = 0; i < 75; i++) {
                String line = bf1.readLine();
                String[] nums = line.split("\\s+");
                Flower flower=new Flower(Double.parseDouble(nums[0]),Double.parseDouble(nums[1]),Double.parseDouble(nums[2]),Double.parseDouble(nums[3]),Integer.parseInt(nums[4]));
                testList.add(flower);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Flower> getTrainList() {
        return trainList;
    }

    public static List<Flower> getTestList() {
        return testList;
    }

    //测试
    public static void test(){
        int temp=0;
        List<Integer> list=new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        TreeNode treeNode = Tools.generateTree(trainList, list);
        System.out.println(treeNode);
        for (int i = 0; i < testList.size(); i++) {
            if(Tools.isRight(trainList.get(i),treeNode)){
                temp++;
            }
        }
//        System.out.println("测试准确率为:"+(double) temp/testList.size());
    }

    public static void main(String[] args) {
        test();

    }

}

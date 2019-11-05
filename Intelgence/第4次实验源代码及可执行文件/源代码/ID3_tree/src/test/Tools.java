package test;

import java.util.*;

public class Tools {
    public static double getInfoD(List<Flower> list){//获得一个集合的先验熵
        int size = list.size();
        int typeOne=0;
        int typeTwo=0;
        int typeThree=0;
        for (Flower flower : list) {
            if(flower.getType()==1){//第一种花
                typeOne++;
            }else if(flower.getType()==2){//第二种花
                typeTwo++;
            }else{//第三种花
                typeThree++;
            }
        }
        return getD(typeOne,size)+getD(typeTwo,size)+getD(typeThree,size);
    }

    public static double getD(int a,int b){
        if(a==0){
            return 0.0;
        }
        double x=(double)a/b;
        return -(x)*(Math.log(x)/Math.log(2.0));
    }

//    public double getInfoAd(List<Flower> list,String str){//获取一个集合的某个属性的后验熵,str为属性名称
//        if(str=="height"){
//
//        }else if(str=="weight"){
//
//        }else if(str=="leaf_length"){
//
//        }else{
//
//        }
//        return 1.0;
//    }

    //构造决策树,properties为存有1，2，3，4的属性集
    public static TreeNode generateTree(List<Flower> flowers,List<Integer> properties){//必须传入数据集和属性集
        if(flowers.isEmpty()){//数据集为空
            return null;
        }else if(isOneType(flowers)){//数据集为同一类别
            return new TreeNode(null,flowers.get(0).getType(),null);//属性为null，类型为。。。
        }else if(properties.isEmpty()){//属性集为空，则不能继续划分，选择最大的作为节点
            int endType = getEndType(flowers);
            return new TreeNode(null,endType,null);
        }else{//数据集不同类，且属性不空，则需要继续分
            Map<Double,Integer> maxS=new HashMap<>();
            for (int i = 0; i < properties.size(); i++) {//获取每个属性的最大信息增益
                List<Double> maxIncOfOneProperty = getMaxIncOfOneProperty(flowers, properties.get(i));
                maxS.put(maxIncOfOneProperty.get(0),properties.get(i));//key:最大增益，value:对应的属性
            }
            List<Double> keys=new ArrayList<>(maxS.keySet());//
            Collections.sort(keys);
            Integer integer = maxS.get(keys.get(keys.size() - 1));
            properties.remove(integer);//从properties中删除该属性
            double infoD = getInfoD(flowers);
            List<Double> maxIncOfOneProperty = getMaxIncOfOneProperty(flowers, integer);
            Info infoAd = getInfoAd(flowers, integer,maxIncOfOneProperty.get(1) , infoD);
            TreeNode root=new TreeNode(integer,null,maxIncOfOneProperty.get(1));
            root.setLeft(generateTree(infoAd.getSmall(),properties));
            root.setRight(generateTree(infoAd.getBig(),properties));
            return root;
        }
    }

    //由于数据都是连续的，所以必须连续值处理,针对每个属性来找到最合适的划分点，获得最大的信息增益,并同时返回最大增益的划分值
    public static List<Double> getMaxIncOfOneProperty(List<Flower> flowers, int index){//需传入数据集和属性index
        double infoD = getInfoD(flowers);//该数据集的先验熵
//        System.out.println(infoD);
        double temp=0;
        double point=0;
        List<Double> allPointsOfOneProperty = getAllPointsOfOneProperty(flowers, index);//获得所有划分点
        for (Double aDouble : allPointsOfOneProperty) {//针对每个划分点，做操作
//            System.out.println(aDouble);//问题点
            double infoAd = getInfoAd(flowers, index, aDouble, infoD).getInfoAd();
//            System.out.println(infoAd);//问题点
            if (infoAd > temp) {
                temp = infoAd;
//                System.out.println(temp);
                point = aDouble;
            }
        }
        List<Double> list = new ArrayList<>();
        list.add(temp);//第一个值为该属性最大的增益值
//        System.out.println(temp);
        list.add(point);//划分点
        return list;
    }

    //获取一个数据集某个属性的全部划分点
    public static List<Double> getAllPointsOfOneProperty(List<Flower> flowers, int index){//传入数据集和，属性inde
        List<Double> all=new ArrayList<>();
        for (int i = 0; i < flowers.size(); i++) {//先将该属性值请全部取出来
            all.add(flowers.get(i).getValue(index));
        }
        Collections.sort(all);//对属性值排序
        List<Double> newAll=new ArrayList<>();
        for (int i = 0; i < flowers.size()-1; i++) {
            double mid=(all.get(i)+all.get(i+1))/2;
            newAll.add(mid);
        }
        return newAll;
    }

    //针对某一个划分点，求出通过该划分点得到的info
    public static Info getInfoAd(List<Flower> flowers,int index,double sidePoint,double infoD){//参数数据集，属性index,划分点数据,infoD为先验熵
        List<Flower> flowers1=new ArrayList<>();//属性值小于sidePoint的集合
        List<Flower> flowers2=new ArrayList<>();//属性值大于sidePoint的集合
        for (int i = 0; i < flowers.size(); i++) {
            if(flowers.get(i).getValue(index)<sidePoint){
                flowers1.add(flowers.get(i));
            }else{
                flowers2.add(flowers.get(i));
            }
        }
        double infoD1 = getInfoD(flowers1);
//        System.out.println(infoD1);
        double infoD2 = getInfoD(flowers2);
//        System.out.println(infoD2);
        double infoAd = infoD - (double) flowers1.size() / flowers.size() * infoD1 - (double) flowers2.size() / flowers.size() * infoD2;
//        System.out.println(infoAd);
        return new Info(flowers1,flowers2,infoAd,index);
    }

    public static boolean isOneType(List<Flower> flowers){//判断一组集合是否属于同一种类别
        int type = flowers.get(0).getType();
        for (int i = 0; i < flowers.size(); i++) {
            if(flowers.get(i).getType()!=type){
                return false;
            }
        }
        return true;
    }

    //如果分类属性用完了，单集合中并不是同一类，则默认为种类最多的类
    public static int getEndType(List<Flower> flowers){
        int typeOne=0;
        int typeTwo=0;
        int typeThree=0;
        for (Flower flower : flowers) {
            if(flower.getType()==1){//第一种花
                typeOne++;
            }else if(flower.getType()==2){//第二种花
                typeTwo++;
            }else{//第三种花
                typeThree++;
            }
        }
        if(typeOne>=typeTwo && typeOne>=typeThree){
            return 1;
        }else if(typeTwo>=typeThree){
            return 2;
        }else{
            return 3;
        }
    }

    public static int testInt(Flower flower,TreeNode root){
        if(root.getType()!=null){//类别不为空，说明已经找到类型
            return root.getType();
        }
        Integer property = root.getProperty();
        double value = flower.getValue(property);
        if(value<root.getPoint()){//小于，往左走
            return testInt(flower,root.getLeft());
        }else{
            return testInt(flower,root.getRight());
        }
    }

    public static boolean isRight(Flower flower,TreeNode root){
        if(flower.getType()==testInt(flower,root)){
            return true;
        }else{
            return false;
        }
    }

}

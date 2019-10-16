package Test;

import java.util.*;

public class StateGraph {
    private Node[][] stateTwoArray=new Node[3][3];//每个状态的二维节点数组
    private int gn;//每个节点已经走过的步数
    private int manHaDunDistance;
    private int fnVal;
    private StateGraph parent;


    //构造状态函数
    public StateGraph(int[][] intArray, int gn) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.stateTwoArray[i][j]=new Node(i,j,intArray[i][j]);
            }
        }
        this.gn = gn;
    }

    public StateGraph(StateGraph one) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.stateTwoArray[i][j]=new Node(i,j,one.getStateTwoArray()[i][j].getVal());
            }
        }
        this.gn=one.getGn();
    }

    public Node[][] getStateTwoArray() {
        return stateTwoArray;
    }

    public void setStateTwoArray(Node[][] stateTwoArray) {
        this.stateTwoArray = stateTwoArray;
    }

    public int getGn() {
        return gn;
    }

    public void setGn(int gn) {
        this.gn = gn;
    }

    //判断两个状态equals的条件，为set的contain方法做准备
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateGraph that = (StateGraph) o;
        Node[][] thisArray = this.getStateTwoArray();
        Node[][] thatArray= that.getStateTwoArray();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(thisArray[i][j].getVal()!=thatArray[i][j].getVal()){
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(stateTwoArray);
    }


    public int getManHaDunDistance(StateGraph dest) {//获得曼哈顿距离,和设置此节点的曼哈顿距离
        int sum=0;
        Map<Integer,Node> map1=new HashMap<Integer, Node>();
        Map<Integer,Node> map2=new HashMap<Integer, Node>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                map1.put(this.stateTwoArray[i][j].getVal(),this.stateTwoArray[i][j]);
                map2.put(dest.stateTwoArray[i][j].getVal(),dest.stateTwoArray[i][j]);
            }
        }
        for (int i = 1; i < 9; i++) {
            sum+=Math.abs(map1.get(i).getHang()-map2.get(i).getHang());
            sum+=Math.abs(map1.get(i).getLie()-map2.get(i).getLie());
        }
        this.manHaDunDistance=sum;//设置此节点的曼哈顿距离
        return sum;
    }


    public int getFnVal() {
        return fnVal;
    }


    public void setFnVal(int fnVal) {
        this.fnVal = fnVal;
    }

    public StateGraph getParent() {
        return parent;
    }

    public void setParent(StateGraph parent) {
        this.parent = parent;
    }
    public List<StateGraph> getNeighbors(){//获取可到达的后继节点集合
        List<StateGraph> neighbors=new ArrayList<>();//邻列表
        Node[][] state = this.getStateTwoArray();//获得当前状态的数组
        Map<Integer,Node> map=new HashMap<Integer, Node>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                map.put(state[i][j].getVal(),state[i][j]);
            }
        }
        Node node = map.get(0);//获得0的节点
        int hang = node.getHang();//获得0节点行号
        int lie = node.getLie();//获得0节点列号
        if(hang>0){//行大于0，可将上方节点加入
            StateGraph stateUp=new StateGraph(this);//复制一份当前状态
            stateUp.setGn(stateUp.getGn()+1);
            swap(stateUp.getStateTwoArray()[hang][lie],stateUp.getStateTwoArray()[hang-1][lie]);//将复制状态的0节点与其上的交换
            neighbors.add(stateUp);
        }
        if(hang<2){//行小于2,可将下方节点加入
            StateGraph stateDown=new StateGraph(this);//复制一份当前状态
            stateDown.setGn(stateDown.getGn()+1);
            swap(stateDown.getStateTwoArray()[hang][lie],stateDown.getStateTwoArray()[hang+1][lie]);//将复制状态的0节点与其上的交换
            neighbors.add(stateDown);
        }
        if(lie<2){//列小于2，可将右方节点加入
            StateGraph stateRight=new StateGraph(this);//复制一份当前状态
            stateRight.setGn(stateRight.getGn()+1);
            swap(stateRight.getStateTwoArray()[hang][lie],stateRight.getStateTwoArray()[hang][lie+1]);//将复制状态的0节点与其上的交换
            neighbors.add(stateRight);
        }
        if(lie>0){//列大于0，可将左方节点加入
            StateGraph stateLeft=new StateGraph(this);//复制一份当前状态
            stateLeft.setGn(stateLeft.getGn()+1);
            swap(stateLeft.getStateTwoArray()[hang][lie],stateLeft.getStateTwoArray()[hang][lie-1]);//将复制状态的0节点与其上的交换
            neighbors.add(stateLeft);
        }
        return neighbors;
    }
    public void swap(Node node1,Node node2){//一个状态中交换两个节点位置
        int val1 = node1.getVal();
        node1.setVal(node2.getVal());
        node2.setVal(val1);
    }

    @Override
    public String toString() {
        StringBuilder str=new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                str.append(stateTwoArray[i][j].getVal()+" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    public void setManHaDunDistance(int manHaDunDistance) {
        this.manHaDunDistance = manHaDunDistance;
    }
    public boolean isCanBeSolved(StateGraph state){
        Node[][] thisArray = this.getStateTwoArray();
        Node[][] thatArray = state.getStateTwoArray();
        TreeSet<Integer> thisSet=new TreeSet<>();
        TreeSet<Integer> thatSet=new TreeSet<>();
        int thisNum=0;
        int thatNum=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(thisArray[i][j].getVal()!=0){
                    thisNum += thisSet.tailSet(thisArray[i][j].getVal()).size();
                    thisSet.add(thisArray[i][j].getVal());
                }
                if(thatArray[i][j].getVal()!=0){
                    thatNum += thatSet.tailSet(thatArray[i][j].getVal()).size();
                    thatSet.add(thatArray[i][j].getVal());
                }
            }
        }
        if(thisNum%2==thatNum%2){
            return true;
        }else{
            return false;
        }

    }
}

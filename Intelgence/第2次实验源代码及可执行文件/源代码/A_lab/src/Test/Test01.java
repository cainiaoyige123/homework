package Test;

import javax.print.attribute.standard.Destination;
import java.util.*;

public class Test01 {
    private static StateGraph dest=new StateGraph(new int[][]{{1,2,3},{8,0,4},{7,6,5}},0);//目标状态
    private TreeSet<StateGraph> open=new TreeSet<StateGraph>(new myComparator());//open表存放未展开的状态
    private TreeSet<StateGraph> closed=new TreeSet<StateGraph>(new myComparator());//closed表存放访问过的状态

    public StateGraph findPath(StateGraph begin){//此函数用来寻找路径 最后返回最末节点状态，一个反向链表的尾部
        begin.setGn(0);//设置gn
        begin.getManHaDunDistance(dest);//设置曼哈顿距离
        begin.setFnVal(begin.getGn()+begin.getManHaDunDistance(dest));//设置f值
        open.add(begin);//将开始状态加入open表
        StateGraph bestNode=null;
//        int a=0;
        while(!open.isEmpty()){//open不空
            /*System.out.println("............");
            for (StateGraph stateGraph : open) {
                System.out.println(stateGraph);
            }
            System.out.println("...............");*/
            bestNode=open.pollFirst();//获得f值最小的元素,open中删除此状态
//            System.out.println(bestNode);
            closed.add(bestNode);//closed中加入此状态
            if(bestNode.equals(dest)){//找到目标节点
                return bestNode;
            }else {//未找到目标节点状态，则将此节点状态扩展
                List<StateGraph> neighbors = bestNode.getNeighbors();
                for (StateGraph neighbor : neighbors) {//遍历每个邻节点状态
                    neighbor.getManHaDunDistance(dest);
                    neighbor.setFnVal(neighbor.getGn()+neighbor.getManHaDunDistance(dest));
//                    System.out.println(neighbor);
                    neighbor.setParent(bestNode);//确定当前邻节点状态的父节点
                    if(open.contains(neighbor)){//如果当前节点在open表中
                        StateGraph old = open.ceiling(neighbor);//找出和neighbor相等的状态
                        open.remove(old);//从open中删除old
                        if(neighbor.getGn()<old.getGn()){//该邻节点的gn小
                            open.add(neighbor);
                        }
                    }else if(closed.contains(neighbor)){//如果当前邻节点在closed表中
                        StateGraph old=closed.ceiling(neighbor);
                        closed.remove(old);//取出old
                        if(old.getFnVal()>neighbor.getFnVal()){//old的f值大，则需要替换
                            open.add(neighbor);
                        }
                    }else{//不在open，closed中,直接加到open中
                        open.add(neighbor);
                    }
                }
            }
//            System.out.println(1);
//            if(a>2){
//                break;
//            }
//            a++;
        }
        return null;
    }
    public void printPath(StateGraph begin){
        StateGraph head = findPath(begin);
        StateGraph temp=head;
        Stack<StateGraph> roadState=new Stack<StateGraph>();
        if(temp==null){
            System.out.println("无有效解法");
        }else{
            while(temp!=null){
                roadState.push(temp);
                temp=temp.getParent();
            }
            while(!roadState.empty()){
                System.out.println(roadState.pop());
            }
        }
    }

    public static void main(String[] args) {
        Test01 test=new Test01();
        int[][] beginArray=new int[][]{{2,4,8},{7,0,3},{6,5,1}};//248703615//课本：283104765//213705864
        StateGraph begin=new StateGraph(beginArray,0);
        if(!begin.isCanBeSolved(dest)){
            System.out.println("该状态无解");
        }else{
            System.out.println("状态变换路径如下：");
            test.printPath(begin);
        }

    }
}

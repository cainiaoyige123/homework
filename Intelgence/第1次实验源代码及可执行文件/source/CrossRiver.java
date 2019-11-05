package crossRiver;

import java.util.*;

public class CrossRiver {
    private LeftBankState head=null;
    private int NumML;
    private int NumCl;

    public CrossRiver(int numML,int numCl) {
        this.NumML=numML;
        this.NumCl=numCl;
        creatMap();
    }

    public LeftBankState getHead() {
        return head;
    }

    public void creatMap(){//实现所有的状态路径
        head=new LeftBankState(NumML,NumCl,1);
        Queue<LeftBankState> states=new LinkedList<>();
        states.offer(head);
        Set<LeftBankState> stateSet=new HashSet<>();
        stateSet.add(head);
        LeftBankState curr=head;
        while((curr=states.peek())!=null){//队列未排完，则继续循环
            if((curr.getML()!=0 || curr.getBL()!=0 || curr.getCL()!=0)){//只要有一个不为0，则说明未到终点状态，则继续操作
                if(curr.getBL()==1){//船在左岸
                    for (Operation operation:Operations.getAllOperation()){
                        int houML=curr.getML()-operation.getCarryM();
                        int houCL=curr.getCL()-operation.getCarryC();
                        if(isRight(houML,houCL) && (curr.getOperation()==null || !equals(operation,curr.getOperation()))){//符合条件
                            LeftBankState state=new LeftBankState(houML,houCL,0);
                            if(stateSet.contains(state)){
                                continue;
                            }
                            state.setOperation(operation);
                            curr.getNextStates().add(state);
                            states.offer(state);
                            stateSet.add(state);
                        }
                    }
                }else{//船在右岸
                    for(Operation operation:Operations.getAllOperation()){
                        int houML=curr.getML()+operation.getCarryM();
                        int houCL=curr.getCL()+operation.getCarryC();
                        if(isRight(houML,houCL) && (curr.getOperation()==null || !equals(curr.getOperation(),operation))){//符合条件
                            LeftBankState state=new LeftBankState(houML,houCL,1);
                            if(stateSet.contains(state)){
                                continue;
                            }
                            state.setOperation(operation);
                            curr.getNextStates().add(state);
                            states.offer(state);
                            stateSet.add(state);

                        }
                    }
                }
            }
            states.poll();
        }
    }

    public boolean isRight(int houML,int houCL){//判断该操作后的状态是否正确
        if((houCL>=0 && houCL<=NumCl) && (houML>=0 && houML<=NumML)){
            if(houML==0 || houML==NumML){//当左岸上人数为0或3时，则符合
                return true;
            }else{//为1或2时，则只有人和野人数相等才符合
                if(NumML==NumCl){
                    if(houCL==houML){
                        return true;
                    }else{
                        return false;
                    }
                }else{
                    if(houML>=houCL && NumML-houML>=NumCl-houCL){
                        return true;
                    }else{
                        return false;
                    }
                }
            }
        }else{//人数不在规定范围内，则不符合
            return false;
        }
    }

    public boolean equals(Operation op1,Operation op2){
        if(op1.getCarryM()==op2.getCarryM() && op1.getCarryC()==op2.getCarryC()){
            return true;
        }else{
            return false;
        }
    }
    public void printStates(LeftBankState headState){
        Queue<LeftBankState> queue=new LinkedList<>();
        queue.offer(headState);
        while(queue.peek()!=null){
            LeftBankState curr=queue.poll();
            System.out.println(curr);
            System.out.println("..........................................................");
            for(LeftBankState state:curr.getNextStates()){
                queue.offer(state);
            }
        }
    }
}

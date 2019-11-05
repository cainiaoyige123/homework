package Test;

import java.util.Comparator;

public class myComparator implements Comparator<StateGraph> {
    private StateGraph dest=new StateGraph(new int[][]{{1,2,3},{8,0,4},{7,6,5}},0);//目标状态

    //比较两个状态的f值，便于从open表中选取最优状态
    @Override
    public int compare(StateGraph o1, StateGraph o2) {
        int o1Fn = o1.getGn() + o1.getManHaDunDistance(dest);//o1的f值
        int o2Fn = o2.getGn() + o2.getManHaDunDistance(dest);//o2的f值
        if(o1Fn<o2Fn){
            return -1;
        }else{
            return 1;
        }
    }

    public StateGraph getDest() {
        return dest;
    }

    public void setDest(StateGraph dest) {
        this.dest = dest;
    }
}

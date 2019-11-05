package crossRiver;

public class Test {
    public static void main(String[] args) {
        int numML=Integer.parseInt(args[0]);
        int numCL=Integer.parseInt(args[1]);
        boolean flag=false;
        if(numML>numCL){
            flag=true;
        }else{
            if(numML == numCL){
                if(numCL<=3){
                    flag=true;
                }
            }
        }
        if(flag){
            CrossRiver crossRiver=new CrossRiver(numML,numCL);
            LeftBankState head = crossRiver.getHead();
            crossRiver.printStates(head);
        }else {
            System.out.println("无安全渡河方案(当传教士人数多余野人数，或者传教士人数等于野人且传教士人数小于三才能有安全渡河方案)！");
        }
    }
}

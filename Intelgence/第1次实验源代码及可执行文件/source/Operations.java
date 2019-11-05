package crossRiver;

import java.util.ArrayList;
import java.util.List;

public class Operations {
    public static List<Operation> getAllOperation(){
        List<Operation> allOperation=new ArrayList<>();
        allOperation.add(new Operation(0,1));//F={P01，P10，P11，P02，P20，Q01，Q10，Q11，Q02，Q20}
        allOperation.add(new Operation(1,0));
        allOperation.add(new Operation(1,1));
        allOperation.add(new Operation(0,2));
        allOperation.add(new Operation(2,0));
        return allOperation;
    }
}

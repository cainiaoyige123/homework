package crossRiver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeftBankState  {
    private int ML;
    private int CL;
    private int BL;
    private Operation operation;
    private List<LeftBankState> nextStates=new ArrayList<>();

    public List<LeftBankState> getNextStates() {
        return nextStates;
    }

    public void setNextStates(List<LeftBankState> nextStates) {
        this.nextStates = nextStates;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public int getML() {
        return ML;
    }

    public void setML(int ML) {
        this.ML = ML;
    }

    public int getCL() {
        return CL;
    }

    public void setCL(int CL) {
        this.CL = CL;
    }

    public int getBL() {
        return BL;
    }

    public void setBL(int BL) {
        this.BL = BL;
    }

    public LeftBankState(int ML, int CL, int BL) {
        this.ML = ML;
        this.CL = CL;
        this.BL = BL;
    }

    @Override
    public String toString() {
        return "LeftBankState{" +
                "ML=" + ML +
                ", CL=" + CL +
                ", BL=" + BL +
                ", operation=" + operation +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeftBankState state = (LeftBankState) o;
        return ML == state.ML &&
                CL == state.CL &&
                BL == state.BL;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ML, CL, BL);
    }
}

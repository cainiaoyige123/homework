package crossRiver;

public class Operation {
    private int carryM;
    private int carryC;

    public int getCarryM() {
        return carryM;
    }

    public void setCarryM(int carryM) {
        this.carryM = carryM;
    }

    public int getCarryC() {
        return carryC;
    }

    public void setCarryC(int carryC) {
        this.carryC = carryC;
    }

    public Operation(int carryM, int carryC) {
        this.carryM = carryM;
        this.carryC = carryC;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "carryM=" + carryM +
                ", carryC=" + carryC +
                '}';
    }
}

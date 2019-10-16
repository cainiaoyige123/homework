package Test;

public class Node {
    private int hang;
    private int lie;
    private int val;

    public int getHang() {
        return hang;
    }

    public int getLie() {
        return lie;
    }

    public int getVal() {
        return val;
    }

    public Node(int hang, int lie, int val) {
        this.hang = hang;
        this.lie = lie;
        this.val = val;
    }

    @Override
    public String toString() {
        return "Node{" +
                "hang=" + hang +
                ", lie=" + lie +
                ", val=" + val +
                '}';
    }

    public void setHang(int hang) {
        this.hang = hang;
    }

    public void setLie(int lie) {
        this.lie = lie;
    }

    public void setVal(int val) {
        this.val = val;
    }

}

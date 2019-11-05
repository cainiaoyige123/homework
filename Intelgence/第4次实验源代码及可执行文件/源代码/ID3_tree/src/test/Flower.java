package test;

public class Flower {
    private double height;
    private double weight;
    private double leaf_length;
    private double leaf_width;
    private int type;

    @Override
    public String toString() {
        return "Flower{" +
                "height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", leaf_length='" + leaf_length + '\'' +
                ", leaf_width='" + leaf_width + '\'' +
                ", type=" + type +
                '}';
    }

    public Flower(double height, double weight, double leaf_length, double leaf_width, int type) {
        this.height = height;
        this.weight = weight;
        this.leaf_length = leaf_length;
        this.leaf_width = leaf_width;
        this.type = type;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getLeaf_length() {
        return leaf_length;
    }

    public void setLeaf_length(double leaf_length) {
        this.leaf_length = leaf_length;
    }

    public double getLeaf_width() {
        return leaf_width;
    }

    public void setLeaf_width(double leaf_width) {
        this.leaf_width = leaf_width;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getValue(int index){//index只能为1，2，3，4
        if(index==1){
            return getHeight();
        }else if(index==2){
            return getWeight();
        }else if(index==3){
            return getLeaf_length();
        }else{
            return getLeaf_width();
        }
    }

}

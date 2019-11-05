package test;

public class TreeNode {
    private Integer property;
    private Integer type;
    private Double point;
    private TreeNode left;
    private TreeNode right;

    public Double getPoint() {
        return point;
    }

    public void setPoint(Double point) {
        this.point = point;
    }

    public TreeNode(Integer property, Integer type,Double point) {
        this.property = property;
        this.type = type;
        this.point=point;
    }

    public Integer getProperty() {
        return property;
    }

    public void setProperty(Integer property) {
        this.property = property;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public TreeNode getLeft() {
        return left;
    }

    public void setLeft(TreeNode left) {
        this.left = left;
    }

    public TreeNode getRight() {
        return right;
    }

    public void setRight(TreeNode right) {
        this.right = right;
    }

//    @Override
//    public String toString() {
//        if(this.getProperty()==null){
//            return "TreeNode{" +
//                    ", type=" + type +
//                    '}';
//        }else{
//            return "TreeNode{" +
//                    "property=" + property +
//                    ", point=" + point +
//                    '}';
//        }
//    }


    @Override
    public String toString() {
        return "TreeNode{" +
                "property=" + property +
                ", type=" + type +
                ", point=" + point +
                ", left=" + left +
                ", right=" + right +
                '}';
    }
}

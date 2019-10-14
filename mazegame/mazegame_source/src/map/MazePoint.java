package map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MazePoint {
    private int val;
    private int x;//行号
    private int y;//列号
    private LinkedList<Direction> directions=new LinkedList<>();

    @Override
    public String toString() {
        return "MazePoint{" +
                "val=" + val +
                ", x=" + x +
                ", y=" + y +
                '}';
    }

    public MazePoint(int val, int x, int y) {
        this.val = val;
        this.x = x;
        this.y = y;
        directions.add(new Direction(0,-1));
        directions.add(new Direction(1,0));
        directions.add(new Direction(0,1));
        directions.add(new Direction(-1,0));
    }

    public LinkedList<Direction> getDirections() {
        return directions;
    }


    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
    public List<MazePoint> getNeighbors(Maze maze){//获得一个通点的所有邻墙
        List<MazePoint> neighbors=new ArrayList<>();
        MazePoint[][] mazeData=maze.getMaze();
        MazePoint up=mazeData[this.x-1][this.y];//上邻墙
        if(up.x!=0){//上邻墙不在上边界上
            if(up.val==1){//如果上邻墙未打通，则加入到邻墙列里
                neighbors.add(up);
            }
        }
        MazePoint right=mazeData[this.x][this.y+1];//右邻墙
        if(right.y!=maze.getLenX()+1){//右邻墙不在右边界上
            if(right.val==1){//如果右邻墙未打通，则加入到邻墙列里
                neighbors.add(right);
            }
        }
        MazePoint down=mazeData[this.x+1][this.y];//下邻墙
        if(down.x!=maze.getLenY()+1){//下邻墙不在下边界上
            if(down.val==1){//如果下邻墙未打通，则加入到邻墙列里
                neighbors.add(down);
            }
        }
        MazePoint left=mazeData[this.x][this.y-1];//左邻墙
        if(left.y!=0){//左邻墙不在左边界上
            if(left.val==1){//如果左邻墙未打通，则加入到邻墙列里
                neighbors.add(left);
            }
        }
        return neighbors;
    }

}

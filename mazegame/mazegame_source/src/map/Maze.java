package map;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Maze {
    private int lenX;
    private int lenY;
    public MazePoint[][] maze;

    public int getLenX() {
        return lenX;
    }

    public int getLenY() {
        return lenY;
    }

    public MazePoint[][] getMaze() {
        return maze;
    }

    public Maze(int lenX, int lenY) {
        this.lenX = lenX;
        this.lenY = lenY;
        maze=new MazePoint[lenY+2][lenX+2];
        for (int i = 0; i < lenY+2; i++) {
            for (int j = 0; j < lenX+2; j++) {
                if(i==0 || i==lenY+1 || j==0 || j==lenX+1){//填充边界为墙1
                    maze[i][j]=new MazePoint(1,i,j);
                }else{
                    if(i%2==1){//为奇数行
                        if(j%2==1){//为奇数列
                            maze[i][j]=new MazePoint(0,i,j);
                        }else{//为偶数列
                            maze[i][j]=new MazePoint(1,i,j);
                        }
                    }else {//为偶数行
                        maze[i][j]=new MazePoint(1,i,j);
                    }
                }
            }
        }
        creatMazeData();
    }

    public void creatMazeData(){
        List<MazePoint> walls=new ArrayList<>();//墙的列表
        maze[1][1].setVal(2);//路径首地址
        List<MazePoint> neighbors = maze[1][1].getNeighbors(this);//获取首地址的邻墙
        walls.addAll(neighbors);//邻墙全部加入墙列表walls
        while(!walls.isEmpty()){//墙的列表不空，则继续循环
            int randomNum=(int) (Math.random()*walls.size());//产生随机数
            MazePoint randomWall = walls.get(randomNum);
            int x=randomWall.getX();//获得行号
            int y=randomWall.getY();//获得列号
            if(x%2==0){//行号为偶，则处理该墙上下
                //如果上下都已访问过（val都为2），则不做操作，如果该墙上下有一个未访问（不都为2），则打通墙（将该墙和不是2的都只为2）
                int upVal = maze[x - 1][y].getVal();
                int downVal = maze[x + 1][y].getVal();
                if(!(upVal==2 && downVal==2)){
                    randomWall.setVal(2);
                    if (upVal == 2) {
                        maze[x + 1][y].setVal(2);
                        walls.addAll(maze[x + 1][y].getNeighbors(this));
                    } else {
                        maze[x - 1][y].setVal(2);
                        walls.addAll(maze[x - 1][y].getNeighbors(this));
                    }
                }
            }else{//行号为奇，则处理左右
                int leftVal = maze[x][y - 1].getVal();//左值
                int rightVal = maze[x][y + 1].getVal();//右值
                if(!(leftVal==2 && rightVal==2)){//左右不全文2
                    randomWall.setVal(2);
                    if (leftVal == 2) {//左边为2，设置右边
                        maze[x][y+1].setVal(2);
                        walls.addAll(maze[x][y+1].getNeighbors(this));
                    } else {
                        maze[x][y-1].setVal(2);
                        walls.addAll(maze[x][y-1].getNeighbors(this));
                    }
                }
            }
            walls.remove(randomWall);//无论如何，该墙都要删掉
        }
    }

    public void findPath(){//0代表路径，1代表墙壁，2代表路
        Stack<MazePoint> roadStack=new Stack<>();
        MazePoint head=maze[1][1];
        MazePoint temp=maze[1][1];
        roadStack.push(head);
        while(!(temp.getX()==lenX && temp.getY()==lenY)){
            temp.setVal(0);//temp为栈顶元素，已经访问过设置为0
            while(true){//死循环，直到找到合适的方向
                if(!temp.getDirections().isEmpty()){//当前节点的方向队列不空，则继续找
                    Direction currDirection = temp.getDirections().poll();//获得下一节点的方向
                    if(isDirectionOk(temp,currDirection)){
                        int x =temp.getX() + currDirection.getX();//获得下一节点x
                        int y =temp.getY() + currDirection.getY();//获得下一节点y
                        roadStack.push(maze[x][y]);
                        break;
                    }
                }else{//当前节点的方向队列为空，说明当前节点无下一个通路口，则从路径栈里删除该节点
                    MazePoint out = roadStack.pop();
                    out.setVal(2);
                    break;
                }
            }
            temp = roadStack.peek();
        }
        maze[lenX][lenY].setVal(0);
    }
    public boolean isDirectionOk(MazePoint mazePoint,Direction direction){//确定某个节点的附近一个方向的节点是否可加入栈
        int x = mazePoint.getX() + direction.getX();
        int y = mazePoint.getY() + direction.getY();
        if(maze[x][y].getVal()==2){
            return true;
        }else{
            return false;
        }
    }
}

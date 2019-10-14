package map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    public static void main(String[] args) {
        Maze maze=new Maze(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
        MazePoint[][] a=maze.maze;
        JFrame jFrame=new JFrame("迷宫控制窗口");
        FlowLayout flowLayout=new FlowLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        jFrame.setLayout(flowLayout);
        JButton jButton1=new JButton("生成迷宫");
        jFrame.add(jButton1);
        JButton jButton2=new JButton("寻找路径");
        jFrame.add(jButton2);
        jFrame.setBounds(200,200,300,110);
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                print(a);
            }
        });
        jButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                maze.findPath();
                print(a);
            }
        });

    }

    private static void print(MazePoint[][] a) {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if(a[i][j].getVal()==2){
                    System.out.print("  ");
                }else if(a[i][j].getVal()==1){
                    System.out.print("0 ");
                }else{
                    System.out.print("# ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}

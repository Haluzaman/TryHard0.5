package main;

import javax.swing.*;

/**
 * Created by bubof on 05.07.2017.
 */
public class GamePanel extends JFrame {

    public static void main(String[] args){
        JFrame gamePanel = new JFrame("Hra");
        gamePanel.setResizable(false);
        gamePanel.setContentPane(new Game());
        gamePanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        gamePanel.setExtendedState(JFrame.MAXIMIZED_BOTH);
//        gamePanel.setUndecorated(true);
        gamePanel.setVisible(true);
        gamePanel.pack();
    }
}

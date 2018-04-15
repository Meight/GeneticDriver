package Mains;

import org.newdawn.slick.SlickException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainLauncher{

    private static JFrame jFrame;

    public static void main(String [] args){



        JPanel jPanel = new JPanel(new GridLayout(3,1));

        JButton trainIA = new JButton("Entrainer une intelligence artificielle");
        JButton jouerIA = new JButton("Jouer contre la meilleure IA");
        JButton jouerRZ = new JButton("Jouer avec un ami interconnecté en LAN sans VPN");

        jPanel.add(trainIA);
        jPanel.add(jouerIA);
        jPanel.add(jouerRZ);

        trainIA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    closeWindow();
                    WindowGameAI.launch();
                } catch (SlickException e1) {
                    e1.printStackTrace();
                }
            }
        });

        jouerIA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    closeWindow();
                    WindowGame.launch();
                } catch (SlickException e1) {
                    e1.printStackTrace();
                }
            }
        });

        jouerRZ.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                closeWindow();
                NetworkMain.launch();
            }
        });



        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jFrame.setContentPane(jPanel);
        jFrame.setBounds(0, 0, 500, 250);
        jFrame.setVisible(true);

    }

    public static void closeWindow(){
        jFrame.setVisible(false);
    }


}

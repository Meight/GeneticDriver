package Mains;

import Model.Network.Client;
import Model.Network.Server;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkMain {

    JFrame jFrame;


    public NetworkMain() {
        jFrame = new JFrame();
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = networkPanel();
        jFrame.setContentPane(panel);
        jFrame.setBounds(0, 0, 500, 250);
        jFrame.setVisible(true);
    }

    public static void launch() {
        new NetworkMain();
    }

    public JPanel networkPanel(){

        JPanel jPanel = new JPanel(new GridLayout(1,2));

        JButton client = new JButton("Rejoindre une room");
        JButton serveur = new JButton("Créer une room");

        jPanel.add(client);
        jPanel.add(serveur);

        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client actu = new Client(jFrame);
                showWindows(false);
            }
        });

        serveur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Server.openWindows();
                showWindows(false);
            }
        });

        return jPanel;
    }


    public void showWindows(boolean show){
        jFrame.setVisible(show);
    }
}
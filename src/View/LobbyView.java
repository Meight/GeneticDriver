package View;

import Controller.LobbyController;
import Model.Game.Lobby;
import Model.Game.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class LobbyView {

    LobbyController control;

    public LobbyView(LobbyController ctrl){
        control = ctrl;
        lobbyRoom();
    }

    public static void lobbyRoom(){
        JFrame fenetre = new JFrame();
        JPanel panel = new JPanel(new BorderLayout());
        JLabel labelLobby = new JLabel("Lobby");
        panel.add(labelLobby,BorderLayout.NORTH);


        fenetre.setContentPane(panel);
        fenetre.setSize(400, 100);
        fenetre.setDefaultCloseOperation(EXIT_ON_CLOSE);
        fenetre.setVisible(true);
    }

}

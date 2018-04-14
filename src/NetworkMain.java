import Controller.LobbyController;
import Model.Game.Lobby;
import Model.Game.Player;
import Model.Game.Track.Track;
import Model.Network.Client;
import Model.Network.Server;
import View.LobbyView;
import org.dyn4j.geometry.Vector2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NetworkMain extends JFrame {

    private static Client myClient;
    private static Server myServer;
    private static JTextField nameServ;
    private static JButton searchServer;
    private static JLabel who;

    public NetworkMain() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = networkPanel();
        setContentPane(panel);
        setBounds(0, 0, 500, 250);
        setVisible(true);
    }

    public static void main(String[] args) {
        new NetworkMain();
    }

    public JPanel networkPanel(){
        /*JPanel cli = new JPanel(new BorderLayout());
        searchServer = new JButton("Chercher les serveurs");
        JButton client = new JButton("Client");
        cli.add(searchServer,BorderLayout.PAGE_END);
        cli.add(client,BorderLayout.PAGE_START);
        searchServer.setEnabled(false);

        JPanel serv = new JPanel(new BorderLayout());
        JButton serveur = new JButton("Serveur");
        serv.add(serveur,BorderLayout.PAGE_END);
        nameServ = new JTextField();
        serv.add(nameServ,BorderLayout.PAGE_START);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(cli,BorderLayout.LINE_START);
        panel.add(serv,BorderLayout.LINE_END);
        who = new JLabel("WHO I AM");
        panel.add(who,BorderLayout.CENTER);

        searchServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myClient.DEBUG_getServers();
                try {
                    myClient.foundServers();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        client.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionClient();
            }
        });
        serveur.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                actionServer();
            }
        });
        return panel;*/



        JPanel jPanel = new JPanel(new GridLayout(1,2));

        JButton client = new JButton("Rejoindre une room");
        JButton serveur = new JButton("Créer une room");

        jPanel.add(client);
        jPanel.add(serveur);

        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Client actu = new Client();
                actu.openWindows();
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

    private static void actionClient(){
        who.setText("Je suis le client");
        searchServer.setEnabled(true);
        if(myServer != null){
            myServer.stopServer();
        }
        myClient = new Client();
    }

    public void showWindows(boolean show){
        this.setVisible(show);
    }
}
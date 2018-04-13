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
    private static  JLabel who;

    public NetworkMain() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = networkPanel();
        setContentPane(panel);
        setBounds(0, 0, 800, 800);
        setVisible(true);
    }

    public static void main(String[] args) {
        new NetworkMain();
    }

    public static JPanel networkPanel(){
        JPanel cli = new JPanel(new BorderLayout());
        searchServer = new JButton("Chercher les serveurs");
        JButton client = new JButton("Client");
        cli.add(searchServer,BorderLayout.SOUTH);
        cli.add(client,BorderLayout.NORTH);
        searchServer.setEnabled(false);

        JPanel serv = new JPanel(new BorderLayout());
        JButton serveur = new JButton("Serveur");
        serv.add(serveur,BorderLayout.SOUTH);
        nameServ = new JTextField();
        serv.add(nameServ,BorderLayout.NORTH);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(cli,BorderLayout.WEST);
        panel.add(serv,BorderLayout.EAST);
        who = new JLabel("WHO I AM");
        panel.add(who,BorderLayout.PAGE_START);

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
        return panel;
    }

    private static void actionServer(){
        if(nameServ.getText().length() > 0){
            searchServer.setEnabled(false);
            who.setText("Je suis le serveur "+nameServ.getText());
            myServer = new Server(nameServ.getText());
            myServer.start();
        }
    }

    private static void actionClient(){
        who.setText("Je suis le client");
        searchServer.setEnabled(true);
        if(myServer != null){
            myServer.stopServer();
        }
        myClient = new Client();
    }
}
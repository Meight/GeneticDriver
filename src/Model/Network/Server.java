package Model.Network;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server extends Thread{

    private DatagramSocket socket;
    static JTextField nameServ;
    static JPanel jPanel;
    static JLabel jLabel;
    static JFrame jFrame;
    private String serverName;
    private boolean running;
    private byte[] buf = new byte[BUFFER_SIZE];
    private static final int SERVER_PORT  = 13594;
    private static final int BUFFER_SIZE = 8192;
    private static final String SERVERS = "getServers";
    private static final String ACCEPT = "go";
    private ServerGame sg;

    private Server(String name) {
        try{
            socket = new DatagramSocket(SERVER_PORT);
            serverName = name;
            jLabel = new JLabel("En attente de votre ami sans VPN");
            checkServeur();
        }catch (Exception E){}
    }

    public void run() {
        running = true;
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                String received = new String(packet.getData(), 0, packet.getLength());
                if(received.equalsIgnoreCase(SERVERS)){
                    buf = new byte[BUFFER_SIZE];
                    buf = serverName.getBytes();
                    packet = new DatagramPacket(buf, buf.length, address, port);
                    socket.send(packet);
                }else if(received.equalsIgnoreCase(ACCEPT)){
                    System.out.println("Serveur créé");
                    close();
                    running = false;
                    sg = new ServerGame(address);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public static void openWindows() {
        jFrame = new JFrame();
        jPanel = new JPanel(new GridLayout(1,1));
        jFrame.add(jPanel);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setBounds(0, 0, 500, 250);
        jFrame.setTitle("Server Side");
        nameServ = new JTextField("Michel");

        jPanel.add(jLabel);
        jFrame.setVisible(true);
    }

    private static void checkServeur(){
        if(nameServ.getText().length() > 0){
            Server s = new Server(nameServ.getText());
            s.start();
        }
    }

    private static void close(){
        jFrame.removeAll();
        jFrame.validate();
        jFrame.setVisible(false);
    }
}

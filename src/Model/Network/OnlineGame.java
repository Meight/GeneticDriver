package Model.Network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class OnlineGame extends Thread{

    InetAddress server;
    private DatagramSocket socket;
    private final static int portClient = 33333;
    private final static int acceptGame = 13594;
    private final static int portServer = 33334;
    boolean running = true;
    private byte[] buf = new byte[BUFFER_SIZE];
    private static final int BUFFER_SIZE = 8192;
    private static final String ACCEPT = "go";

    public OnlineGame (InetAddress s){
        server = s;
        try{
            socket = new DatagramSocket(portClient);
        }catch (Exception E){}
    }

    public void run(){
        try {
        buf = ACCEPT.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, server, acceptGame);
        socket.send(packet);
        NetworkGame.launch();
        while (running){
                //todo buf prend input du joueur
                packet = new DatagramPacket(buf, buf.length, server, portServer);
                socket.send(packet);
                buf = new byte[BUFFER_SIZE];
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                //todo received VERS le jeu (nouvelles positions)
                buf = new byte[BUFFER_SIZE];

        }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

}

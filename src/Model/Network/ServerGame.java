package Model.Network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerGame extends Thread{

    private DatagramSocket socket;
    private InetAddress client;
    private final static int portClient = 33333;
    private final static int portServer = 33334;
    boolean running = true;
    private byte[] buf = new byte[BUFFER_SIZE];
    private static final int BUFFER_SIZE = 8192;

    public ServerGame(InetAddress cclient){
        client = cclient;
        try{
            socket = new DatagramSocket(portServer);
        }catch (Exception E){}
    }

    public void run() {try {
        NetworkGame.launch();
        while (running) {

                buf = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                String received = new String(packet.getData(), 0, packet.getLength());
                //todo received VERS le jeu (input du joueur distant)
                buf = new byte[BUFFER_SIZE];
                //todo buf prend nouvelle position des deux voitures en Serialized
                packet = new DatagramPacket(buf, buf.length, client, portClient);
                socket.send(packet);
        }
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

}

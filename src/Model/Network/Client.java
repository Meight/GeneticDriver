package Model.Network;

//import org.aspectj.org.eclipse.jdt.internal.core.SourceType;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */

public class Client {
    private int port;
    private DatagramSocket socket;
    private List<String> nomServer = new ArrayList<>();
    private List<InetAddress> addressServer = new ArrayList<>();
    private static final String SERVERS = "getServers";
    private static final int BUFFER_SIZE = 8192;
    private static final int CLIENT_PORT  = 13584;
    private static final long DELTATIME = 30000;
    private byte[] buf = new byte[BUFFER_SIZE];

    public Client() {
        try {
            socket = new DatagramSocket(CLIENT_PORT);
            this.port = CLIENT_PORT;
            foundServers();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public DatagramPacket readPacket() {
        byte[] buffer = new byte[BUFFER_SIZE];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length); //Todo change logic
        return packet;
    }

    public void sendPacket(byte[] buffer) {
        // Todo.
    }

    public void foundServers(){
        try {
            socket = new DatagramSocket(BUFFER_SIZE);
            socket.setBroadcast(true);
            buf = SERVERS.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.send(packet);
            long begin = System.currentTimeMillis();
            while (begin + DELTATIME > System.currentTimeMillis()){
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                addressServer.add(address);
                String received = new String(packet.getData(), 0, packet.getLength());
                nomServer.add(received);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DEBUG_getServers(){
        for(int i = 0; i < nomServer.size(); ++i){
            System.out.println("Nom du serveur : "+nomServer.get(i));
            System.out.println("Adresse du serveur : "+addressServer.get(i));
            System.out.println();
        }
    }
}

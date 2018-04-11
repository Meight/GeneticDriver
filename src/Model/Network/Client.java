package Model.Network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */

public class Client {
    private int port;

    private List<Server> listServer = new ArrayList<>();

    private static final int BUFFER_SIZE = 8192;

    private DatagramSocket socket;

    public Client(int port) {
        this.port = port;
    }

    public DatagramPacket readPacket() {
        byte[] buffer = new byte[8192];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        return packet;
    }

    public void sendPacket(byte[] buffer) {
        // Todo.
    }

    public void foundServers(){
        try {
            socket = new DatagramSocket(BUFFER_SIZE);
            socket.setBroadcast(true);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}

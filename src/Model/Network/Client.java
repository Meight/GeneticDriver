package Model.Network;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author Matthieu Le Boucher
 */

public class Client {
    private int port;

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
}

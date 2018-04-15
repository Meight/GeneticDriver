package Model.Network;

import org.dyn4j.geometry.Vector2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ThreadSendServer extends Thread {

    private DatagramSocket socket;
    private final static int portServer = 33334;
    private final static int portClient = 33333;
    private InetAddress client;
    boolean running = true;
    boolean sending = false;
    private static final int BUFFER_SIZE = 8192;
    private byte[] buf = new byte[BUFFER_SIZE];

    public ThreadSendServer(InetAddress address){
        client = address;
        try {
            socket = new DatagramSocket(portServer+54);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while (running) {
            if(sending){
                System.out.println("SEND FROM SERVER");
                DatagramPacket packet = new DatagramPacket(buf, buf.length, client, portClient);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buf = new byte[BUFFER_SIZE];
                sending = false;
            }
        }
    }

    public void sendPos(Vector2 pos, double angle){
        if(!sending){
            String envoi = pos.x + "," + pos.y+ "," + angle;
            buf = envoi.getBytes();
            sending = true;
        }
    }
}

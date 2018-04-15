package Model.Network;

import org.dyn4j.geometry.Vector2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ThreadSendClient extends Thread {

    boolean running = true;
    private DatagramSocket socket;
    InetAddress server;
    static boolean sending = false;
    private static final int BUFFER_SIZE = 8192;
    private static byte[] buf = new byte[BUFFER_SIZE];
    private static final String ACCEPT = "go";
    private final static int acceptGame = 13594;
    private final static int portClient = 33333;
    private final static int portServer = 33334;
    DatagramPacket packet;

    public ThreadSendClient(InetAddress s){
        server = s;
        try {
            socket = new DatagramSocket(portClient+5);
        } catch (SocketException e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }


    @Override
    public void run() {
        super.run();
        buf = ACCEPT.getBytes();
        packet = new DatagramPacket(buf, buf.length, server, acceptGame);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        buf = new byte[BUFFER_SIZE];
        while (running) {
            System.out.println("I CAN SEND CLIENT");
            if (sending){
                System.out.println("SEND FROM CLIENT");
                packet = new DatagramPacket(buf, buf.length, server, portServer);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println(e);
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

package Model.Network;

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
        packet = new DatagramPacket(buf, buf.length, server, acceptGame);
        try {
            socket = new DatagramSocket(portClient+12);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        buf = ACCEPT.getBytes();
        packet = new DatagramPacket(buf, buf.length, server, acceptGame);
        try {
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        buf = new byte[BUFFER_SIZE];
    }


    @Override
    public void run() {
        super.run();
        while (running) {
            System.out.println("TRY SEND");
            if (sending){
                System.out.println("SEND FROM CLIENT");
                sending = false;
                packet = new DatagramPacket(buf, buf.length, server, portServer);
                try {
                    socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buf = new byte[BUFFER_SIZE];
            }
        }
    }

    public void sendInput(String serial, int delta) {
        String envoi = serial + "," + delta;
        buf = envoi.getBytes();
        sending = true;
    }
}

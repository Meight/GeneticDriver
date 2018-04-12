package Model.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server extends Thread{

    private DatagramSocket socket;
    private String serverName;
    private boolean running;
    private byte[] buf = new byte[BUFFER_SIZE];
    private static final int SERVER_PORT  = 13594;
    private static final int BUFFER_SIZE = 8192;
    private static final String SERVERS = "getServers";

    public Server(String name) {
        try{
            socket = new DatagramSocket(SERVER_PORT);
            serverName = name;
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
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        socket.close();
    }

    public synchronized void stopServer(){
        running = false;
    }
}

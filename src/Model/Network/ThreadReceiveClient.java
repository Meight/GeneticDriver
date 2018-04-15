package Model.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ThreadReceiveClient extends Thread {

    boolean running = true;
    private final static int portClient = 33333;
    private static final int BUFFER_SIZE = 8192;
    private static byte[] buf = new byte[BUFFER_SIZE];
    private DatagramPacket packet;
    private DatagramSocket socket;

    public ThreadReceiveClient(){
        try {
            socket= new DatagramSocket(portClient);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while(running) {
            packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("RECU CLIENT : "+received);
            //todo received VERS le jeu (nouvelles positions)
            buf = new byte[BUFFER_SIZE];
        }
    }
}

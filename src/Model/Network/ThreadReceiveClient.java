package Model.Network;

import org.dyn4j.geometry.Vector2;

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
    private Vector2 pos;
    private double angle;

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
            buf = new byte[BUFFER_SIZE];
            packet = new DatagramPacket(buf, buf.length);
            try {
                socket.receive(packet);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("RECU FROM SERVEUR : "+received);
            String[] inpt = received.split(",");
            pos=new Vector2(new Float(inpt[0]),new Float(inpt[1]));
            angle=new Double(inpt[2]);
            buf = new byte[BUFFER_SIZE];
        }
    }

    public Vector2 getPos() {
        return pos;
    }

    public double getAngle() {
        return angle;
    }
}

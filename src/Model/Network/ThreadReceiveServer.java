package Model.Network;

import org.dyn4j.geometry.Vector2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ThreadReceiveServer extends Thread {

    private final static int portServer = 33334;
    private DatagramSocket socketRecept;
    private static final int BUFFER_SIZE = 8192;
    private byte[] buf = new byte[BUFFER_SIZE];
    boolean running = true;
    private Vector2 pos;
    private double angle;



    public ThreadReceiveServer() {
        try {
            socketRecept = new DatagramSocket(portServer);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();
        while(running) {
            System.out.println("RECEIVE FROM SERVER");
            buf = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            try {
                socketRecept.receive(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String received = new String(packet.getData(), 0, packet.getLength());
            System.out.println("RECU SERVEUR : "+received);
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

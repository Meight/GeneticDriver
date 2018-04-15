package Model.Network;

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
    private Input inputFromClient;
    private int deltaFromClient = 0;



    public ThreadReceiveServer() {
        try {
            socketRecept = new DatagramSocket(portServer);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        inputFromClient = new Input(false,false,false);
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
            inputFromClient = new Input(inpt[0],inpt[1],inpt[2]);
            deltaFromClient = new Integer(inpt[3]);
            buf = new byte[BUFFER_SIZE];
        }
    }

    public int getDelta(){
        return deltaFromClient;
    }

    public Input getInput(){
        return inputFromClient;
    }
}

package Model.Network;

import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class OnlineGame extends Thread{

    InetAddress server;
    private DatagramSocket socket;
    private DatagramSocket socketRecept;
    private final static int portClient = 33333;
    private final static int acceptGame = 13594;
    private final static int portServer = 33334;
    boolean running = true;
    Thread envoiPacket;
    Thread recuPaquet;
    static boolean sending = false;
    private static final int BUFFER_SIZE = 8192;
    private static byte[] buf = new byte[BUFFER_SIZE];
    private static byte[] buff = new byte[BUFFER_SIZE];
    private static final String ACCEPT = "go";
    final DatagramPacket[] packet = {new DatagramPacket(buf, buf.length, server, acceptGame),new DatagramPacket(buff, buff.length)};

    public OnlineGame (InetAddress s){
        server = s;
        try{
            socket = new DatagramSocket(portClient+12);
            socketRecept = new DatagramSocket(portClient);
        }catch (Exception E){}

        envoiPacket = new Thread() {
            @Override
            public void run() {
                super.run();
                while (running) {
                    System.out.println("TRY SEND");
                    if (sending){
                        System.out.println("SEND FROM CLIENT");
                        sending = false;
                        packet[0] = new DatagramPacket(buf, buf.length, server, portServer);
                        try {
                            socket.send(packet[0]);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        buf = new byte[BUFFER_SIZE];
                    }
                }
            }
        };

        recuPaquet = new Thread(){
            @Override
            public void run() {
                super.run();
                while(running) {
                    packet[1] = new DatagramPacket(buff, buff.length);
                    try {
                        socketRecept.receive(packet[1]);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    String received = new String(packet[1].getData(), 0, packet[1].getLength());
                    System.out.println("RECU CLIENT : "+received);
                    //todo received VERS le jeu (nouvelles positions)
                    buff = new byte[BUFFER_SIZE];
                }
            }
        };

    }

    public void run() {
        try {
            System.out.println("CLIENT SIDE");
            buf = ACCEPT.getBytes();

            socket.send(packet[0]);
            buf = new byte[BUFFER_SIZE];
            try {
                NetworkGame.launch(this);
            } catch (SlickException e1) {
                e1.printStackTrace();
            }
            recuPaquet.start();
            envoiPacket.start();
            
            }catch (IOException e1){
                e1.printStackTrace();
            }
    }

    public static void sendInput(String serial, int delta) {
        String envoi = serial + "," + delta;
        buf = envoi.getBytes();
        sending = true;
    }
}

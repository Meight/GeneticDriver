package Model.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerGame extends Thread{

    private DatagramSocket socket;
    private DatagramSocket socketRecept;
    private InetAddress client;
    private final static int portClient = 33333;
    private final static int portServer = 33334;
    Thread envoiPacket;
    Thread recuPaquet;
    boolean running = true;
    boolean sending = false;
    private byte[] buf = new byte[BUFFER_SIZE];
    private static final int BUFFER_SIZE = 8192;

    public ServerGame(InetAddress cclient){
        client = cclient;
        try{
            socket = new DatagramSocket(portServer+54);
            socketRecept = new DatagramSocket(portServer);
        }catch (Exception E){}
        envoiPacket = new Thread() {
            @Override
            public void run() {
                super.run();
                while (running) {
                    if(sending){
                        sending = false;
                        //todo buf prend nouvelle position des deux voitures en Serialized
                        DatagramPacket packet = new DatagramPacket(buf, buf.length, client, portClient);
                        try {
                            socket.send(packet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };
        recuPaquet = new Thread(){
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
                    //todo received VERS le jeu (input du joueur distant)
                    buf = new byte[BUFFER_SIZE];
                }
            }
        };
    }

    public void run() {
        try {
            System.out.println("SERVER SIDE");
            NetworkGame.launch(this);
            recuPaquet.start();
            envoiPacket.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}

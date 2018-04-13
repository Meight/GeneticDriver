package Model.Network;

//import org.aspectj.org.eclipse.jdt.internal.core.SourceType;

import java.io.IOException;
import java.net.*;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */

public class Client {
    private int port;
    private DatagramSocket socket;
    private List<String> nomServer = new ArrayList<>();
    private List<InetAddress> addressServer = new ArrayList<>();
    private static final String SERVERS = "getServers";
    private static final int BUFFER_SIZE = 8192;
    private static final int CLIENT_PORT  = 13584;
    private static final int SERVER_PORT  = 13594;
    private static final long DELTATIME = 1000;
    private byte[] buf = new byte[BUFFER_SIZE];

    public Client() {
        try {
            socket = new DatagramSocket(CLIENT_PORT);
            socket.setBroadcast(true);
            this.port = CLIENT_PORT;
            //foundServers();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void foundServers() throws Exception {
            System.out.println("1");
            buf = SERVERS.getBytes();
            final InetAddress[] address = {InetAddress.getByName("255.255.255.255")};
            final DatagramPacket packet = new DatagramPacket(buf, buf.length, address[0], SERVER_PORT);
            System.out.println("2");
            socket.send(packet);
            System.out.println("3");
            long begin = System.currentTimeMillis();
            Thread search = new Thread(){
                @Override
                public void run() {
                    super.run();
                    while (true){
                        try {
                            socket.receive(packet);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        address[0] = packet.getAddress();
                        addressServer.add(address[0]);
                        String received = new String(packet.getData(), 0, packet.getLength());
                        nomServer.add(received);
                    }
                }
            };
            search.start();
            while (begin + DELTATIME > System.currentTimeMillis()){
                System.out.println("44444");
            }
            search.stop();
            System.out.println("5");
        }


    public void DEBUG_getServers(){
        for(int i = 0; i < nomServer.size(); ++i){
            System.out.println("Nom du serveur : "+nomServer.get(i));
            System.out.println("Adresse du serveur : "+addressServer.get(i));
            System.out.println();
        }
    }
}

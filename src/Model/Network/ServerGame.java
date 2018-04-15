package Model.Network;

import org.newdawn.slick.SlickException;
import java.net.InetAddress;

public class ServerGame{

    private InetAddress client;
    private ThreadReceiveServer receiveServer;
    private ThreadSendServer sendServer;

    public ServerGame(InetAddress cclient){
        client = cclient;
        System.out.println("SERVER SIDE");
        receiveServer = new ThreadReceiveServer();
        sendServer = new ThreadSendServer(client);
        receiveServer.start();
        sendServer.start();
        try {
            NetworkGame.launch(this);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}

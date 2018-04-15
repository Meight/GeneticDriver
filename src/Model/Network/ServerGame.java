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
    }

    public void launch(){
        receiveServer.start();
        sendServer.start();
    }



}

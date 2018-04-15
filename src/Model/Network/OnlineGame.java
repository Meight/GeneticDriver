package Model.Network;

import org.newdawn.slick.SlickException;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class OnlineGame{

    private InetAddress server;
    private ThreadReceiveClient receiveClient;
    private ThreadSendClient sendClient;

    public OnlineGame (InetAddress s){
        server = s;
        receiveClient = new ThreadReceiveClient();
        sendClient = new ThreadSendClient(server);
        receiveClient.start();
        sendClient.start();
        try {
            NetworkGame.launch(this);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }


    public ThreadSendClient getSendClient() {
        return sendClient;
    }

    public ThreadReceiveClient getReceiveClient() {
        return receiveClient;
    }
}

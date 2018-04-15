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
        try {
            NetworkGame.launch(this);
        } catch (SlickException e) {
            e.printStackTrace();
        }
        receiveClient = new ThreadReceiveClient();
        sendClient = new ThreadSendClient(server);
    }

    public void launch(){
        receiveClient.start();
        sendClient.start();
    }

    public ThreadSendClient getSendClient() {
        return sendClient;
    }
}

package Model.Network;

import Mains.MainLauncher;
import Model.Game.Car;
import Model.Game.Player;
import Model.Game.RenderableObject;
import Model.KeyPressedListener;
import org.newdawn.slick.*;
import org.newdawn.slick.Input;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class NetworkGame extends BasicGame {
    private GameContainer container;
    private TiledMap map;
    private static ServerGame serverGame = null;
    private static OnlineGame onlineGame = null;
    private List<NetworkPlayer> players = new ArrayList<NetworkPlayer>();
    private List<KeyPressedListener> keyPressedListeners = new ArrayList<KeyPressedListener>();

    public NetworkGame() {
        super("GeneticDriver");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        this.map = new TiledMap("maps/Map.tmx");

        players.add(new NetworkPlayer("Matt", map, false,false));
        players.add(new NetworkPlayer("Distant", map, false,true));
        for (Player player : players) {
            keyPressedListeners.add(player.getCar());
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        this.map.render(0, 0);

        for (NetworkPlayer player : players) {
            RenderableObject car = player.getCar();
            car.render(container, g);
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        for (NetworkPlayer player : players) {
            if(player.IsDistant()){
                if(serverGame == null){
                    RenderableObject car = player.getCar();
                    car.setPosition(onlineGame.getReceiveClient().getPos());
                    car.setAngle(onlineGame.getReceiveClient().getAngle());
                }else{
                    RenderableObject car = player.getCar();
                    car.setPosition(serverGame.getReceiveServer().getPos());
                    car.setAngle(serverGame.getReceiveServer().getAngle());
                }

            }else{
                RenderableObject car = player.getCar();
                Model.Network.Input i = InputFactory.generateInput(container);
                car.processInput(i, delta);
                if(serverGame == null){
                    onlineGame.getSendClient().sendPos(car.getPosition(),car.getAngle());
                }else {
                    serverGame.getSendServer().sendPos(car.getPosition(),car.getAngle());
                }
            }

        }
    }

    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
            this.container.exit();
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        for(KeyPressedListener object : keyPressedListeners) {
            object.keyPressed(key, c);
        }
    }

    public static void launch(ServerGame pserverGame) throws SlickException {
        serverGame = pserverGame;
        AppGameContainer app = new AppGameContainer(new NetworkGame(), 1300, 960, false);
        app.setTargetFrameRate(100);
        app.start();
    }

    public static void launch(OnlineGame ponlineGame) throws SlickException {
        onlineGame = ponlineGame;
        AppGameContainer app = new AppGameContainer(new NetworkGame(), 1300, 960, false);
        app.setTargetFrameRate(100);
        app.start();
    }
}
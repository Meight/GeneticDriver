import Model.Game.Player;
import Model.Game.RenderableObject;
import Model.Game.Track.CarAI;
import Model.KeyPressedListener;
import Model.Network.InputFactory;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class WindowGameAI extends BasicGame {
    private GameContainer container;
    private TiledMap map;

    private List<Player> players = new ArrayList<Player>();
    private List<KeyPressedListener> keyPressedListeners = new ArrayList<KeyPressedListener>();

    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new WindowGameAI(), 1300, 960, false).start();
    }

    public WindowGameAI() {
        super("GeneticDriver");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        this.map = new TiledMap("maps/Map.tmx");

        players.add(new Player("Matt", map, true));
        players.add(new Player("Toon", map, true));
        players.add(new Player("Perdu", map, true));
        players.add(new Player("Rated", map, true));
        players.add(new Player("Dalh", map, true));
        for (Player player : players) {
            keyPressedListeners.add(player.getCar());
        }
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        this.map.render(0, 0);

        for (Player player : players) {
            RenderableObject car = player.getCar();
            car.render(container, g);
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        for (Player player : players) {
            RenderableObject car = player.getCar();
            if(!player.IsAI()){
                car.processInput(InputFactory.generateInput(container), delta);
            }else{
                ((CarAI)car).ProcessNet();
                car.processInput(InputFactory.generateInputFromAI((CarAI)car), delta);
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
}
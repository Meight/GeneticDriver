import Model.Game.Player;
import Model.Game.RenderableObject;
import Model.KeyPressedListener;
import Model.NeuralNetwork.GeneticSystem;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class WindowGameAI extends BasicGame {
    private GameContainer container;
    private TiledMap map;

    private GeneticSystem geneticSys;
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
        this.geneticSys = new GeneticSystem(20,map,2);
        for (Player player : geneticSys.getPlayers()) {
            keyPressedListeners.add(player.getCar());
        }

        /*(new Thread() {
            public void run() {
                NeuralNetworkView neuralNetworkView = new NeuralNetworkView(((CarAI) geneticSys.getPlayers().get(0).getCar()).getNeuralNetwork());
            }
        }).start();*/
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        this.map.render(0, 0);

        for (Player player : geneticSys.getPlayers()) {
            RenderableObject car = player.getCar();
            car.render(container, g);
        }
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        geneticSys.Update(delta);
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
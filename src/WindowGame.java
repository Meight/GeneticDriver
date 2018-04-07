import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

public class WindowGame extends BasicGame {
    private GameContainer container;
    private TiledMap map;

    public static void main(String[] args) throws SlickException {
        new AppGameContainer(new WindowGame(), 960, 960, false).start();
    }

    public WindowGame() {
        super("Lesson 2 :: MapGame");
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;
        this.map = new TiledMap("maps/Map.tmx");
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        this.map.render(0, 0);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
    }

    @Override
    public void keyReleased(int key, char c) {
        if (Input.KEY_ESCAPE == key) {
            this.container.exit();
        }
    }
}
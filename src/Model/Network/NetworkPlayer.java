package Model.Network;

import Model.Game.Car;
import Model.Game.Player;
import javafx.scene.paint.Color;
import org.newdawn.slick.tiled.TiledMap;

public class NetworkPlayer extends Player {

    boolean distant;

    public NetworkPlayer(String name, Color color, boolean isai, Car car) {
        super(name, color, isai, car);
    }

    public NetworkPlayer(String name, TiledMap map, boolean isai) {
        super(name, map, isai);
    }

    public NetworkPlayer(String name, TiledMap map, boolean isai, boolean isDistant) {
        super(name, map, isai);
        distant = isDistant;
    }

    public NetworkPlayer(String name, TiledMap map, String filename) {
        super(name, map, filename);
    }

    public boolean IsDistant(){
        return distant;
    }
}

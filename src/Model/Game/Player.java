package Model.Game;

import Model.Game.Track.CarAI;
import javafx.scene.paint.Color;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Matthieu Boucher
 */
public class Player {
    private String name;

    private Car car;

    private Color color;

    private Boolean isAI;

    private int scoreboardPosition;

    public Player(String name, Color color, boolean isai, Car car) {
        this.name = name;
        this.car = car;
        this.color = color;
        this.isAI = isai;
    }

    public Player(String name, TiledMap map, boolean isai){
        this.name = name;
        this.isAI = isai;
        if(isAI){
            this.car = new CarAI(map, 150, 150);
        }else{
            this.car = new Car(map, 150, 150);
        }

        this.color = Color.color(Math.random(),Math.random(),Math.random());
    }

    public void changeCarColor(Color color) {
        this.color = color;
    }

    public Car getCar() {
        return car;
    }

    public Boolean IsAI() {
        return isAI;
    }
}

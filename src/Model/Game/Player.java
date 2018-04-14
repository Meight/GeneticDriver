package Model.Game;

import javafx.scene.paint.Color;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Matthieu Boucher
 */
public class Player implements Comparable<Player>{
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
            this.car = new CarAI(map, 200, 320);
        }else{
            this.car = new Car(map, 200, 320);
        }

        this.color = Color.color(Math.random(),Math.random(),Math.random());
    }

    public Player(String name, TiledMap map, String filename){
        this.name = name;
        this.isAI = true;
        this.car = new CarAI(map, 200, 320, filename);

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

    public String getName() {
        return name;
    }

    @Override
    public int compareTo(Player o) {
        return ((CarAI)this.car).compareTo((CarAI)o.car);
    }
    @Override
    public String toString() {return getCar().toString(); }
}

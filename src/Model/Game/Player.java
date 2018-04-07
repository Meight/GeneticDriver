package Model.Game;

import javafx.scene.paint.Color;

/**
 * @author Matthieu Boucher
 */
public class Player {
    private String name;

    private Car car;

    private Color color;

    private int scoreboardPosition;

    public Player(String name, Color color, Car car) {
        this.name = name;
        this.car = car;
        this.color = color;
    }

    public Player(String name){
        this.name = name;
        this.car = new Car();
        this.color = Color.color(Math.random(),Math.random(),Math.random());
    }

    public void changeCarColor(Color color) {
        this.color = color;
    }
}

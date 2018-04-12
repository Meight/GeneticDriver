package Model.Game.Track;

import Model.Game.Car;
import Model.NeuralNetwork.Net;
import org.newdawn.slick.tiled.TiledMap;

public class CarAI extends Car {

    private Net neuralNetwork;

    public CarAI(TiledMap map, int x, int y) {
        super(map, x, y);
        System.out.println("Je suis une IA");
    }
}

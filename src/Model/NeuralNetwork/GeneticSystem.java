package Model.NeuralNetwork;

import Model.Game.Player;
import org.newdawn.slick.tiled.TiledMap;

import java.util.ArrayList;
import java.util.List;

public class GeneticSystem {
    List<Player> players;

    public GeneticSystem(int carNumber, TiledMap map) {
        players = new ArrayList<>();
        for(int i =0;i<carNumber;i++){
            players.add(new Player("AI", map, true));
        }
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }
}

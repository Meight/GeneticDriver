package Model.Game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */
public class Lobby {
    private List<Player> players;

    public Lobby(){
        players = new ArrayList<Player>();
    }

    public Lobby(Player p){
        players = new ArrayList<Player>();
        players.add(p);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
}

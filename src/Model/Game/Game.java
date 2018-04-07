package Model.Game;

import java.util.*;

/**
 * @author Matthieu Le Boucher
 */
public class Game {
    private List<Player> players;

    public Game(Player player){
        players = new ArrayList<Player>();
        players.add(player);
    }

    public Player getMaster(){
        return players.get(0);
    }
}

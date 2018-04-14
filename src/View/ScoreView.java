package View;

import Model.Game.Player;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthieu Le Boucher
 */
public class ScoreView {
    private List<Player> players;

    public ScoreView() {
        this.players = new ArrayList<>();
    }

    public void render(Graphics graphics) {
        // Render the best player onto the HUD.
        render(getBestPlayer(), graphics);
    }

    private Player getBestPlayer() {
        double bestScore = 0;
        Player bestPlayer = null;

        for(Player player : players) {
            if(player.getCar().getScore() > bestScore) {
                bestPlayer = player;
                bestScore = player.getCar().getScore();
            }
        }

        return bestPlayer;
    }

    private void render(Player player, Graphics graphics) {
        if(player == null)
            return;

        graphics.resetTransform();
        graphics.setColor(Color.black);
        graphics.drawString("Best player: " + player.getName(), 20, 20);
        graphics.drawString("Laps: " + player.getCar().getLapsAmount(), 20, 40);
        graphics.drawString("Current time: " + player.getCar().getTimeAccumulator(), 20, 60);
        graphics.drawString("Last lap time: " + player.getCar().getLastLapAbsoluteTime(), 20, 80);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Clears the players list held by the score board, except the best player from last generation.
     */
    public void clearPlayerList() {
        Player bestPlayer = getBestPlayer();

        this.players = new ArrayList<>();

        if(bestPlayer != null)
            this.players.add(bestPlayer);
    }
}

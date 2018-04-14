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
    private static final int ALL_TIME_BEST_LEFT_MARGIN = 20;
    private static final int CURRENT_BEST_LEFT_MARGIN = 250;

    private List<Player> players;
    private Player allTimeBestPlayer;

    public ScoreView() {
        this.players = new ArrayList<>();
    }

    public void render(Graphics graphics) {
        // Render the best player onto the HUD.
        render(allTimeBestPlayer, ALL_TIME_BEST_LEFT_MARGIN, graphics);
        render(getIterationBestPlayer(), CURRENT_BEST_LEFT_MARGIN, graphics);
    }

    private Player getIterationBestPlayer() {
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

    private void render(Player player, int leftMargin, Graphics graphics) {
        if(player == null)
            return;

        graphics.resetTransform();

        graphics.setColor(leftMargin == ALL_TIME_BEST_LEFT_MARGIN ? Color.green : Color.black);
        graphics.drawString(leftMargin == ALL_TIME_BEST_LEFT_MARGIN ? "All time best" : "Iteration best", leftMargin, 20);

        graphics.drawString("Best player: " + player.getName(), leftMargin, 40);
        graphics.drawString("Score: " + player.getCar().getScore(), leftMargin, 60);
        graphics.drawString("Laps: " + player.getCar().getLapsAmount(), leftMargin, 80);
        graphics.drawString("Current time: " + player.getCar().getTimeAccumulator() / 1_000 + " s", leftMargin, 100);
        graphics.drawString("Last lap time: " + player.getCar().getLastLapTime() / 1_000 + " s", leftMargin, 120);
        graphics.drawString("Avg. lap time: " + player.getCar().getAverageLapTime() / 1_000 + " s", leftMargin, 140);
        graphics.drawString("Score per sec: " + player.getCar().getScorePerSecond(), leftMargin, 160);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Clears the players list held by the score board, except the best player from last generation.
     */
    public void clearPlayerList() {
        Player iterationBestPlayer = getIterationBestPlayer();

        this.players = new ArrayList<>();

        if(iterationBestPlayer != null) {
            if(allTimeBestPlayer == null || iterationBestPlayer.getCar().getScore() > allTimeBestPlayer.getCar().getScore())
                allTimeBestPlayer = iterationBestPlayer;

            this.players.add(allTimeBestPlayer);
        }

    }
}

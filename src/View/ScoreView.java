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
    private static final Color ALL_TIME_BEST_COLOR = Color.green.darker(0.5f);

    private List<Player> players;
    private PlayerScoreHolder allTimeBestPlayerScore;

    public ScoreView() {
        this.players = new ArrayList<>();
    }

    public void render(Graphics graphics) {
        // Render the best player onto the HUD.
        if(allTimeBestPlayerScore != null)
            render(allTimeBestPlayerScore, ALL_TIME_BEST_LEFT_MARGIN, graphics);

        Player iterationBestPlayer = getIterationBestPlayer();

        if(iterationBestPlayer != null)
            render(new PlayerScoreHolder(iterationBestPlayer), CURRENT_BEST_LEFT_MARGIN, graphics);
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

    private void render(PlayerScoreHolder playerScore, int leftMargin, Graphics graphics) {
        if(playerScore == null)
            return;

        graphics.resetTransform();

        graphics.setColor(leftMargin == ALL_TIME_BEST_LEFT_MARGIN ? ALL_TIME_BEST_COLOR : Color.black);
        graphics.drawString(leftMargin == ALL_TIME_BEST_LEFT_MARGIN ? "All time best" : "Iteration best", leftMargin, 20);

        graphics.drawString("Best player: " + playerScore.getName(), leftMargin, 40);
        graphics.drawString("Score: " + playerScore.getScore(), leftMargin, 60);
        graphics.drawString("Laps: " + playerScore.getLaps(), leftMargin, 80);
        graphics.drawString("Current time: " + playerScore.getCurrentTime() / 1_000 + " s", leftMargin, 100);
        graphics.drawString("Last lap time: " + playerScore.getLastLapTime() / 1_000 + " s", leftMargin, 120);
        graphics.drawString("Avg. lap time: " + playerScore.getAverageLapTime() / 1_000 + " s", leftMargin, 140);
        graphics.drawString("Score per sec: " + playerScore.getScorePerSecond(), leftMargin, 160);
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
            if(allTimeBestPlayerScore == null || iterationBestPlayer.getCar().getScore() > allTimeBestPlayerScore.getScore())
                allTimeBestPlayerScore = new PlayerScoreHolder(iterationBestPlayer);
        }
    }

    private class PlayerScoreHolder {
        private String name;
        private float score;
        private int laps;
        private float currentTime;
        private float lastLapTime;
        private float averageLapTime;
        private float scorePerSecond;

        PlayerScoreHolder(String name, float score, int laps, float currentTime, float lastLapTime, float averageLapTime, float scorePerSecond) {
            this.name = name;
            this.score = score;
            this.laps = laps;
            this.currentTime = currentTime;
            this.lastLapTime = lastLapTime;
            this.averageLapTime = averageLapTime;
            this.scorePerSecond = scorePerSecond;
        }

        PlayerScoreHolder(Player player) {
            this(player.getName(),
                    (float) player.getCar().getScore(),
                    player.getCar().getLapsAmount(),
                    player.getCar().getTimeAccumulator(),
                    player.getCar().getLastLapTime(),
                    player.getCar().getAverageLapTime(),
                    player.getCar().getScorePerSecond());
        }

        public String getName() {
            return name;
        }

        public float getScore() {
            return score;
        }

        public int getLaps() {
            return laps;
        }

        public float getCurrentTime() {
            return currentTime;
        }

        public float getLastLapTime() {
            return lastLapTime;
        }

        public float getAverageLapTime() {
            return averageLapTime;
        }

        public float getScorePerSecond() {
            return scorePerSecond;
        }
    }
}

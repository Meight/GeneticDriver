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
    /**
     * Left margin (in pixels) of the all time best individual score board.
     */
    private static final int ALL_TIME_BEST_LEFT_MARGIN = 20;

    /*
     * Left margin (in pixels) of the current iteration's best individual score board.
     */
    private static final int CURRENT_BEST_LEFT_MARGIN = 250;

    /**
     * All time best individual's score board's color.
     */
    private static final Color ALL_TIME_BEST_COLOR = Color.green.darker(0.5f);

    /**
     * List of the players of the current iteration.
     */
    private List<Player> players;

    /**
     * The all time best score reached so far.
     */
    private PlayerScoreHolder allTimeBestPlayerScore;

    public ScoreView() {
        this.players = new ArrayList<>();
    }

    /**
     * Renders the global score board.
     * @param graphics The graphics context on which to render.
     */
    public void render(Graphics graphics) {
        // Render the best player onto the HUD.
        if(allTimeBestPlayerScore != null)
            render(allTimeBestPlayerScore, ALL_TIME_BEST_LEFT_MARGIN, graphics);

        Player iterationBestPlayer = getIterationBestPlayer();

        if(iterationBestPlayer != null)
            render(new PlayerScoreHolder(iterationBestPlayer), CURRENT_BEST_LEFT_MARGIN, graphics);
    }

    /**
     * Retrieves the iteration's best individual.
     * @return The best individual of the current iteration.
     */
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

    /**
     * Renders a given player score instance. The rendering is displaced to the right depending on what type of score
     * is being rendered (all time best vs. iteration best.)
     * @param playerScore   The score to render.
     * @param leftMargin    The left margin to apply to the rendering.
     * @param graphics      The graphics context on which to render.
     */
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

    /**
     * Holds the scoring information about a player. Useful to avoid deep copies for score handling.
     */
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

        /**
         * Creates a score holder for a given player.
         * @param player The player whose score is to be stored.
         */
        PlayerScoreHolder(Player player) {
            this(player.getName(),
                    (float) player.getCar().getScore(),
                    player.getCar().getLapsAmount(),
                    player.getCar().getTimeAccumulator(),
                    player.getCar().getLastLapTime(),
                    player.getCar().getAverageLapTime(),
                    player.getCar().getScorePerSecond());
        }

        String getName() {
            return name;
        }

        float getScore() {
            return score;
        }

        int getLaps() {
            return laps;
        }

        float getCurrentTime() {
            return currentTime;
        }

        float getLastLapTime() {
            return lastLapTime;
        }

        float getAverageLapTime() {
            return averageLapTime;
        }

        float getScorePerSecond() {
            return scorePerSecond;
        }
    }
}

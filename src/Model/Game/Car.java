package Model.Game;

import Model.KeyPressedListener;
import Model.Network.Input;
import Model.Network.State;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Matthieu Boucher
 *
 * Basic implementation of a 2D controllable car.
 */
public class Car extends RenderableObject implements KeyPressedListener {
    /**
     * Maximal distance between simulation and actual position before snapping the car's position.
     */
    private static final int MAXIMAL_DISTANCE_BEFORE_SNAP = 5;

    /**
     * The increment applied to the car's rotation each time a turning key is pressed.
     */
    private static final double TURN_INCREMENT = .08d;

    /**
     * The maximal speed a car can reach, in pixels per second.
     */
    private static final double MAXIMAL_SPEED = 3.5d;

    /**
     * The increment applied to the car's acceleration each time the acceleration key is pressed.
     */
    private static final double ACCELERATION_INCREMENT = 0.002d;

    /**
     * The decrement applied to the car's acceleration each time the acceleration key is pressed.
     */
    private static final double ACCELERATION_DECREMENT = 0.002d;

    /**
     * The car's turn at any given moment.
     */
    protected double turn;

    /**
     * The current car's speed.
     */
    protected double speed;

    /**
     * Whether or not the car is considered active.
     */
    protected boolean isAlive;

    /**
     * The current score of the car, used for learning purposes.
     */
    protected double score = 0.0;

    /**
     * The current fitness of the car, used for learning purposes.
     */
    protected double fitness = 0.0;

    /**
     * Laps handling variables.
     */
    protected boolean isOnArrivalLine = true;
    private boolean hasCrossedArrivalLineOnce = false;
    protected List<Float> lapsTime;
    protected float lastLapAbsoluteTime = 0;

    /**
     * The absolute time this car has been running.
     */
    protected float timeAccumulator = 0;

    /**
     * The track on which the car lives.
     */
    protected TiledMap map;

    /**
     * Some key directions used for position tracking, by AIs mostly.
     */
    protected Vector2 forward, right, left, diagRight, diagLeft;

    public Car(TiledMap map, int x, int y) {
        this.position = new Vector2(x, y);
        this.turn = 0;
        this.speed = 0;
        this.angle = 0;
        this.map = map;
        this.isAlive = true;
        this.forward = new Vector2(1, 0);
        this.right = new Vector2(forward).rotate(Math.PI / 4);
        this.diagRight = new Vector2(forward).rotate(Math.PI / 8);
        this.left = new Vector2(forward).rotate(-Math.PI / 4);
        this.diagLeft = new Vector2(forward).rotate(-Math.PI / 8);

        this.lapsTime = new ArrayList<>();

        // Assign a random image to this car.
        File imagesDirectory = null;
        try {
            imagesDirectory = new File(ResourceLoader.getResource("cars/").toURI());
            File[] files = imagesDirectory.listFiles();
            Random rand = new Random();
            assert files != null;
            File file = files[rand.nextInt(files.length)];
            this.image = new Image(file.getPath());
        } catch (URISyntaxException | SlickException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(int key, char c) {
        // Nothing to do for now.
    }

    @Override
    public void processInput(Input input, double time) {
        updatePhysics(input, (float) time);
    }

    @Override
    public void processState(State state, double time) {
        Vector2 difference = state.getPosition().subtract(position);

        double distance = difference.getMagnitudeSquared();

        if (distance > MAXIMAL_DISTANCE_BEFORE_SNAP)
            position = state.getPosition();
        else if (distance > 0.1f)
            position.add(difference.multiply(0.1f));

    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);
    }

    /**
     * Updates the car's physique based on the fed input and the time elapsed since last update.
     * @param input     Input to apply to the car.
     * @param deltaTime Time elapsed since last update.
     */
    private void updatePhysics(Input input, float deltaTime) {
        timeAccumulator += deltaTime;

        turn = 0;
        if(input.isTurningRight())
            turn = TURN_INCREMENT * deltaTime;
        else if (input.isTurningLeft())
            turn = -TURN_INCREMENT * deltaTime;

        if(input.isAccelerating())
            speed += ACCELERATION_INCREMENT * deltaTime;
        else {
            if (speed > 0) {
                speed -= Math.min(speed, ACCELERATION_DECREMENT * deltaTime);
            } else if (speed < 0) {
                speed += Math.min(-speed, ACCELERATION_DECREMENT * deltaTime);
            }
        }

        speed = Math.min(MAXIMAL_SPEED, speed);
        double angleContribution = turn * speed;

        double x = Math.cos(angle) * speed;
        double y = Math.sin(angle) * speed;
        double speedPenaltyFactor = getTerrainFactor(position.x + x, position.y + y);

        // Update car's stats.
        if(speedPenaltyFactor > 1) {
            isAlive = false;
        } else {
            score++;
            fitness++;
        }

        updateLaps(position.x + x, position.y + y);

        // Applies penalty factor to the car.
        x /= speedPenaltyFactor;
        y /= speedPenaltyFactor;
        angleContribution /= speedPenaltyFactor;

        // Update the car's state.
        position.add(x, y);
        angle += Math.toRadians(angleContribution);
        rotate((float) angleContribution);
        forward.rotate(Math.toRadians(angleContribution));
        right.rotate(Math.toRadians(angleContribution));
        left.rotate(Math.toRadians(angleContribution));
        diagLeft.rotate(Math.toRadians(angleContribution));
        diagRight.rotate(Math.toRadians(angleContribution));
    }

    private void updateLaps(double x, double y) {
        if(Rules.isArrivalTile(map, x, y)) {
            if(!isOnArrivalLine) {
                isOnArrivalLine = true;

                if(hasCrossedArrivalLineOnce) {
                    lapsTime.add(timeAccumulator - lastLapAbsoluteTime);
                    lastLapAbsoluteTime = timeAccumulator;
                }

                hasCrossedArrivalLineOnce = true;
            }
        } else
            isOnArrivalLine = false;
    }

    /**
     * Returns the speed factor pertaining to the tile (x, y).
     *
     * @param x The x grid-coordinate of the tile to check.
     * @param y The y grid-coordinate of the tile to check.
     * @return The speed factor on that tile.
     */
    private double getTerrainFactor(double x, double y) {
        return Rules.getSpeedPenaltyFactorForTile(map, x, y);
    }

    public float getAverageLapTime() {
        if(lapsTime.isEmpty())
            return 0;

        float sum = 0;

        for(float lapTime : lapsTime) {
            sum += lapTime;
        }

        return sum / lapsTime.size();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public String toString() {
        return Double.toString(fitness) ;
    }

    public int getLapsAmount() {
        return lapsTime.size();
    }

    public float getLastLapTime() {
        return lapsTime.size() > 0 ? lapsTime.get(lapsTime.size() - 1) : 0;
    }

    public float getTimeAccumulator() {
        return timeAccumulator;
    }

    public float getScorePerSecond() {
        return (float) (score / (timeAccumulator / 1_000));
    }

    public float getLastLapAbsoluteTime() {
        return lastLapAbsoluteTime;
    }
}

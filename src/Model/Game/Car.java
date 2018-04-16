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
 */
public class Car extends RenderableObject implements KeyPressedListener {
    private static final int MAXIMAL_DISTANCE_BEFORE_SNAP = 5;

    private static final double TURN_INCREMENT = .08d;
    private static final double MAXIMAL_SPEED = 3.5d;
    private static final double ACCELERATION_INCREMENT = 0.002d;
    private static final double ACCELERATION_DECREMENT = 0.002d;
    private static final double OBSTACLE_PENALTY_FACTOR = 3d;

    protected double turn;

    protected double speed;
    protected boolean isAlive;
    protected double score = 0.0;
    protected double fitness = 0.0;

    protected boolean isOnArrivalLine = true;
    private boolean hasCrossedArrivalLineOnce = false;
    protected List<Float> lapsTime;
    protected float lastLapAbsoluteTime = 0;
    protected float timeAccumulator = 0;

    protected TiledMap map;

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

        File dir = null;
        try {
            dir = new File(ResourceLoader.getResource("cars/").toURI());
            File[] files = dir.listFiles();
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
        checkForArrival(position.x + x, position.y + y);

        x /= speedPenaltyFactor;
        y /= speedPenaltyFactor;
        position.add(x, y);
        angleContribution /= speedPenaltyFactor;

        angle += Math.toRadians(angleContribution);
        rotate((float) angleContribution);
        forward.rotate(Math.toRadians(angleContribution));
        right.rotate(Math.toRadians(angleContribution));
        left.rotate(Math.toRadians(angleContribution));
        diagLeft.rotate(Math.toRadians(angleContribution));
        diagRight.rotate(Math.toRadians(angleContribution));

    }

    private void checkForArrival(double x, double y) {
        if(this.map.getTileImage(
                (int) x / this.map.getTileWidth(),
                (int) y / this.map.getTileHeight(),
                this.map.getLayerIndex("Arrival")) != null) {

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

    private double getTerrainFactor(double x, double y) {
        try {
            if (this.map.getTileImage(
                    (int) x / this.map.getTileWidth(),
                    (int) y / this.map.getTileHeight(),
                    this.map.getLayerIndex("Walls")) != null){
                isAlive = false;
                return 9000;
            } else if (this.map.getTileImage(
                    (int) x / this.map.getTileWidth(),
                    (int) y / this.map.getTileHeight(),
                    this.map.getLayerIndex("Slow")) != null){
                isAlive = false;
                return OBSTACLE_PENALTY_FACTOR;
            } else {
                score++;
                fitness++;
                return 1;
            }

        } catch(ArrayIndexOutOfBoundsException e) {
            isAlive = false;
            return 9000;
        }
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

    public float getAverageLapTime() {
        if(lapsTime.isEmpty())
            return 0;

        float sum = 0;

        for(float lapTime : lapsTime) {
            sum += lapTime;
        }

        return sum / lapsTime.size();
    }

    public float getScorePerSecond() {
        return (float) (score / (timeAccumulator / 1_000));
    }

    public float getLastLapAbsoluteTime() {
        return lastLapAbsoluteTime;
    }
}

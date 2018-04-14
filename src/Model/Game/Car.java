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

import java.io.File;
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

    protected double angle;
    protected double speed;
    protected boolean isAlive;
    protected double score = 0.0;
    protected double fitness = 0.0;
    protected int laps;

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

        File dir = new File("./resources/cars/");
        File[] files = dir.listFiles();
        Random rand = new Random();
        File file = files[rand.nextInt(files.length)];
        try {
            this.image = new Image(file.getPath());
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void keyPressed(int key, char c) {

    }

    @Override
    public void processInput(Input input, double time) {
        /*float currentTime = System.currentTimeMillis();

        if (time < currentTime)
            return; // Ignore packets out of order.

        float deltaTime = (float) (currentTime - time);*/

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
        turn = 0;
        if(input.isTurningRight())
            turn = TURN_INCREMENT * deltaTime; //Math.min(RIGHT_MOST_TURN, 0.5d);
        else if (input.isTurningLeft())
            turn = -TURN_INCREMENT * deltaTime; //Math.max(LEFT_MOST_TURN, -0.5d);

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

    private double getTerrainFactor(double x, double y) {
        try {
            if (this.map.getTileImage(
                    (int) x / this.map.getTileWidth(),
                    (int) y / this.map.getTileHeight(),
                    this.map.getLayerIndex("Walls")) != null){
                isAlive = false;
                return 9000;
            }else if (this.map.getTileImage(
                    (int) x / this.map.getTileWidth(),
                    (int) y / this.map.getTileHeight(),
                    this.map.getLayerIndex("Slow")) != null){
                isAlive = false;
                return OBSTACLE_PENALTY_FACTOR;
            }else{
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
}

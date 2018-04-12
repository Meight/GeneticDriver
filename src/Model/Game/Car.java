package Model.Game;

import Model.KeyPressedListener;
import Model.Network.Input;
import Model.Network.State;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.*;
import org.newdawn.slick.tiled.TiledMap;

/**
 * @author Matthieu Boucher
 */
public class Car extends RenderableObject implements KeyPressedListener {
    private static final int MAXIMAL_DISTANCE_BEFORE_SNAP = 5;

    private static final double LEFT_MOST_TURN = -0.03d;
    private static final double RIGHT_MOST_TURN = 0.03d;
    private static final double MAXIMAL_SPEED = 1.0d;
    private static final double ACCELERATION_INCREMENT = 0.005d;
    private static final double ACCELERATION_DECREMENT = 0.003d;

    private double turn;

    private double angle;
    private double speed;

    private int laps;

    private TiledMap map;

    /**
     * Steer force currently applied to the car.
     */
    private Vector2 steerForce;

    public Car(TiledMap map, int x, int y) {
        this.position = new Vector2(x, y);
        this.turn = 0;
        this.speed = 0;
        this.angle = 0;
        this.map = map;

        try {
            this.image = new Image("cars/red-car.png");
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
        g.setLineWidth(3);

        // Draw velocity.
        g.setColor(Color.green);
        g.drawLine((int) position.x, (int) position.y, (int) (position.x + Math.cos(angle) * 100), (int) (position.y + Math.sin(angle) * 100));

        g.setColor(Color.blue);
        //Vector2 localAcceleration = new Vector2(position).add(acceleration);

        //g.drawLine((int) position.x, (int) position.y, (int) localAcceleration.x, (int) localAcceleration.y);
    }

    private void updatePhysics(Input input, float deltaTime) {
        turn = 0;
        if(input.isTurningRight())
            turn = 0.1; //Math.min(RIGHT_MOST_TURN, 0.5d);
        else if (input.isTurningLeft())
            turn = -0.1; //Math.max(LEFT_MOST_TURN, -0.5d);

        if(input.isAccelerating())
            speed += ACCELERATION_INCREMENT;
        else {
            if (speed > 0) {
                speed -= Math.min(speed, ACCELERATION_DECREMENT);
            } else if (speed < 0) {
                speed += Math.min(-speed, ACCELERATION_DECREMENT);
            }
        }

        speed = Math.min(MAXIMAL_SPEED, speed);
        double angleContribution = turn * speed;
        angle += Math.toRadians(angleContribution);

        double x = Math.cos(angle) * speed;
        double y = Math.sin(angle) * speed;

        if(this.canMoveTo(position.x + x, position.y + y)) {
            position.add(x, y);
            rotate((float) angleContribution);
        } else {
            angle -= Math.toRadians(angleContribution);
        }
    }

    private boolean canMoveTo(double x, double y) {
        return this.map.getTileImage(
                (int) x / this.map.getTileWidth(),
                (int) y / this.map.getTileHeight(),
                this.map.getLayerIndex("Obstacle")) == null;
    }
}

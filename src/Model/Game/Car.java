package Model.Game;

import Model.KeyPressedListener;
import Model.Network.Input;
import Model.Network.State;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.*;

/**
 * @author Matthieu Boucher
 */
public class Car extends RenderableObject implements KeyPressedListener {
    private static final int MAXIMAL_DISTANCE_BEFORE_SNAP = 5;
    /**
     * Current velocity vector of the car.
     */
    private Vector2 velocity;

    /**
     * Target velocity vector of the car, i.e. the next wanted velocity.
     */
    private Vector2 targetVelocity;

    /**
     * Steer force currently applied to the car.
     */
    private Vector2 steerForce;

    public Car(int x, int y) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(10, 10);

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
        float currentTime = System.currentTimeMillis();

        if (time < currentTime)
            return; // Ignore packets out of order.

        float deltaTime = (float) (currentTime - time);

        updatePhysics(input, deltaTime);
    }

    @Override
    public void processState(State state, double time) {
        Vector2 difference = state.getPosition().subtract(position);

        double distance = difference.getMagnitudeSquared();

        if (distance > MAXIMAL_DISTANCE_BEFORE_SNAP)
            position = state.getPosition();
        else if (distance > 0.1f)
            position.add(difference.multiply(0.1f));

        velocity = state.getVelocity();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        super.render(container, g);

        // Draw velocity.
        g.setColor(Color.green);
        g.setLineWidth(3);
        g.drawLine((int) position.x, (int) position.y, (int) (position.x + velocity.x), (int) (position.y + velocity.y));
    }

    private void updatePhysics(Input input, float deltaTime) {
        if(input.isAccelerating()) {
            // Handle acceleration.
        } else {
            // Handle deceleration.
        }
    }
}

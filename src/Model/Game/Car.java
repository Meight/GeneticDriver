package Model.Game;

import Model.KeyPressedListener;
import Model.Network.Input;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author Matthieu Boucher
 */
public class Car extends RenderableObject implements KeyPressedListener {
    private static final float MAX_VELOCITY_MAGNITUDE_SQUARED = 10f;

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
        this.velocity = new Vector2();

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
        if(input.isAccelerating()) {
            // Handle acceleration.
        } else {
            // Handle deceleration.
        }
    }
}

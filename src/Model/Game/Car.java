package Model.Game;

import Model.KeyPressedListener;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Matthieu Boucher
 */
public class Car extends RenderableObject implements KeyPressedListener {
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
    public void update(GameContainer container, int delta) throws SlickException {
        if(container.getInput().isKeyDown(Input.KEY_RIGHT)) {
            rotate(delta);
        } else if(container.getInput().isKeyDown(Input.KEY_LEFT)) {
            rotate(-delta);
        }
    }
}

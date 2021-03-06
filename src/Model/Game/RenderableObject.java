package Model.Game;

import Model.Network.Input;
import Model.Network.NetworkBehavior;
import Model.Network.State;
import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.*;

/**
 * @author Matthieu Le Boucher
 */
public abstract class RenderableObject implements NetworkBehavior {
    protected Image image;

    protected Vector2 position;

    protected float currentRotation = 0f;

    private float scale = 1f;

    protected double angle;


    void init(GameContainer container) throws SlickException {
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(image, (float) position.x - image.getWidth() * scale / 2, (float) position.y - image.getHeight() * scale / 2);
    }

    public abstract void processInput(Input input, double time);

    public abstract void processState(State state, double time);

    protected void rotate(float angle) {
        currentRotation += angle;
        image.setCenterOfRotation(image.getWidth() * scale / 2, image.getHeight() * scale / 2);
        image.setRotation(currentRotation);
    }

    protected void translate(Vector2 movement) {
        position.add(movement);
    }

    public Vector2 getPosition() {
        return this.position;
    }

    public void setPosition(Vector2 v){
        this.position = v;
    }

    public double getAngle(){
        return this.angle;
    }

    public void setAngle(double d){
        rotate((float) Math.toDegrees( d - this.angle ));
        this.angle = d;
    }
}

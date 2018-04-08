package Model.Game;

import org.dyn4j.geometry.Vector2;
import org.newdawn.slick.*;

/**
 * @author Matthieu Le Boucher
 */
public abstract class RenderableObject {
    protected Image image;

    protected Vector2 position;

    protected float currentRotation = 0f;

    private float scale = 1f;



    void init(GameContainer container) throws SlickException {
    }

    public void render(GameContainer container, Graphics g) throws SlickException {
        g.drawImage(image, (float) position.x, (float) position.y);
    }

    public abstract void update(GameContainer container, int delta) throws SlickException;

    protected void rotate(float angle) {
        currentRotation += angle;
        image.setCenterOfRotation(image.getWidth() * scale / 2, image.getHeight() * scale / 2);
        image.setRotation(currentRotation);
    }

    protected void translate(Vector2 movement) {
        position.add(movement);
    }
}
